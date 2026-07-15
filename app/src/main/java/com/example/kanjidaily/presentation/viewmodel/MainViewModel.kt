package com.example.kanjidaily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjidaily.data.entity.KanjiEntity
import com.example.kanjidaily.data.entity.VocabularyEntity
import com.example.kanjidaily.data.repository.DailyPair
import com.example.kanjidaily.data.repository.KanjiDailyRepository
import com.example.kanjidaily.data.repository.ProgressSummary
import com.example.kanjidaily.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: KanjiDailyRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {
    private val selectedLevel = MutableStateFlow("All")
    val daily = MutableStateFlow<DailyPair?>(null)
    val quizState = MutableStateFlow(QuizUiState())

    val settings = settingsRepository.settings.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), com.example.kanjidaily.data.repository.AppSettings(true, "All"))
    val kanji = selectedLevel.flatMapLatest { repository.kanji(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val vocabulary = selectedLevel.flatMapLatest { repository.vocabulary(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val favoriteKanji = repository.favoriteKanji().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val favoriteVocabulary = repository.favoriteVocabulary().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val progress: StateFlow<ProgressSummary?> = repository.progressSummary().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
            daily.value = repository.dailyPair()
            buildQuestion()
        }
    }

    fun setLevel(level: String) {
        selectedLevel.value = level
    }

    fun observeKanji(character: String) = repository.observeKanji(character)
    fun observeVocabulary(id: Int) = repository.observeVocabulary(id)

    fun toggleKanjiFavorite(item: KanjiEntity) = viewModelScope.launch {
        val favorite = !item.isFavorite
        repository.setKanjiFavorite(item.character, favorite)
        daily.value = daily.value?.let {
            if (it.kanji.character == item.character) it.copy(kanji = it.kanji.copy(isFavorite = favorite)) else it
        }
    }

    fun toggleVocabularyFavorite(item: VocabularyEntity) = viewModelScope.launch {
        val favorite = !item.isFavorite
        repository.setVocabularyFavorite(item.id, favorite)
        daily.value = daily.value?.let {
            if (it.vocabulary.id == item.id) it.copy(vocabulary = it.vocabulary.copy(isFavorite = favorite)) else it
        }
    }

    fun markKanjiStudied(character: String) = viewModelScope.launch { repository.markKanjiStudied(character) }
    fun markVocabularyStudied(id: Int) = viewModelScope.launch { repository.markVocabularyStudied(id) }

    fun answerQuiz(option: String) = viewModelScope.launch {
        val state = quizState.value
        if (state.selectedAnswer != null) return@launch
        val correct = option == state.correctAnswer
        repository.recordQuizAnswer(correct)
        quizState.value = state.copy(selectedAnswer = option, isCorrect = correct, score = state.score + if (correct) 1 else 0)
    }

    fun nextQuestion() = viewModelScope.launch { buildQuestion() }

    private suspend fun buildQuestion() {
        val kanji = repository.allKanjiNow()
        val vocab = repository.allVocabularyNow()
        if (kanji.size < 4 || vocab.size < 4) return
        val useKanji = (System.nanoTime() % 2L) == 0L
        if (useKanji) {
            val answer = kanji.random()
            repository.markKanjiStudied(answer.character)
            val options = (kanji.map { it.meaning }.filterNot { it == answer.meaning }.shuffled().take(3) + answer.meaning).shuffled()
            quizState.value = QuizUiState("What does this kanji mean?", answer.character, options, answer.meaning, score = quizState.value.score)
        } else {
            val answer = vocab.random()
            repository.markVocabularyStudied(answer.id)
            val options = (vocab.map { it.meaning }.filterNot { it == answer.meaning }.shuffled().take(3) + answer.meaning).shuffled()
            quizState.value = QuizUiState("What does this word mean?", answer.word, options, answer.meaning, score = quizState.value.score)
        }
    }
}

data class QuizUiState(
    val prompt: String = "Loading quiz...",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: String = "",
    val selectedAnswer: String? = null,
    val isCorrect: Boolean? = null,
    val score: Int = 0
)
