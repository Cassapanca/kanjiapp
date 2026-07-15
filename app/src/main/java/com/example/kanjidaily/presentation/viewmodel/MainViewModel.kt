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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: KanjiDailyRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {
    private val selectedLevel = MutableStateFlow("All")
    val currentLevel: StateFlow<String> = selectedLevel
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
        viewModelScope.launch {
            settingsRepository.settings.collectLatest { appSettings ->
                selectedLevel.value = appSettings.preferredJlpt
            }
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
        state.kanjiCharacter?.let { repository.markKanjiStudied(it) }
        state.vocabularyId?.let { repository.markVocabularyStudied(it) }
        quizState.value = state.copy(selectedAnswer = option, isCorrect = correct, score = state.score + if (correct) 1 else 0)
    }

    fun nextQuestion() = viewModelScope.launch { buildQuestion() }

    private suspend fun buildQuestion() {
        val kanji = repository.allKanjiNow()
        val vocab = repository.allVocabularyNow()
        val canUseKanji = kanji.map { it.meaning }.distinct().size >= 4
        val canUseVocabulary = vocab.map { it.meaning }.distinct().size >= 4

        if (!canUseKanji && !canUseVocabulary) {
            quizState.value = QuizUiState(
                prompt = "Not enough study data yet.",
                question = "Add at least four unique answers to start a quiz.",
                score = quizState.value.score
            )
            return
        }

        val useKanji = when {
            canUseKanji && canUseVocabulary -> (System.nanoTime() % 2L) == 0L
            canUseKanji -> true
            else -> false
        }
        if (useKanji) {
            val answer = kanji.random()
            val options = buildOptions(answer.meaning, kanji.map { it.meaning })
            quizState.value = QuizUiState(
                prompt = "What does this kanji mean?",
                question = answer.character,
                options = options,
                correctAnswer = answer.meaning,
                score = quizState.value.score,
                kanjiCharacter = answer.character
            )
        } else {
            val answer = vocab.random()
            val options = buildOptions(answer.meaning, vocab.map { it.meaning })
            quizState.value = QuizUiState(
                prompt = "What does this word mean?",
                question = answer.word,
                options = options,
                correctAnswer = answer.meaning,
                score = quizState.value.score,
                vocabularyId = answer.id
            )
        }
    }

    private fun buildOptions(correctAnswer: String, allMeanings: List<String>): List<String> {
        val wrongAnswers = allMeanings
            .filterNot { it == correctAnswer }
            .distinct()
            .shuffled()
            .take(3)
        return (wrongAnswers + correctAnswer).shuffled()
    }
}

data class QuizUiState(
    val prompt: String = "Loading quiz...",
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: String = "",
    val selectedAnswer: String? = null,
    val isCorrect: Boolean? = null,
    val score: Int = 0,
    val kanjiCharacter: String? = null,
    val vocabularyId: Int? = null
)
