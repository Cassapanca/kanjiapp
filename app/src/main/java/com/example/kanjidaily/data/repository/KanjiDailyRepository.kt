package com.example.kanjidaily.data.repository

import com.example.kanjidaily.data.dao.KanjiDao
import com.example.kanjidaily.data.dao.ProgressDao
import com.example.kanjidaily.data.dao.VocabularyDao
import com.example.kanjidaily.data.entity.UserProgressEntity
import com.example.kanjidaily.data.seed.SeedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

class KanjiDailyRepository(
    private val kanjiDao: KanjiDao,
    private val vocabularyDao: VocabularyDao,
    private val progressDao: ProgressDao
) {
    fun kanji(level: String) = if (level == "All") kanjiDao.observeAll() else kanjiDao.observeByLevel(level)
    fun vocabulary(level: String) = if (level == "All") vocabularyDao.observeAll() else vocabularyDao.observeByLevel(level)
    fun favoriteKanji() = kanjiDao.observeFavorites()
    fun favoriteVocabulary() = vocabularyDao.observeFavorites()
    fun observeKanji(character: String) = kanjiDao.observeKanji(character)
    fun observeVocabulary(id: Int) = vocabularyDao.observeVocabulary(id)
    fun progress(): Flow<UserProgressEntity?> = progressDao.observeProgress()
    fun progressSummary() = combine(
        kanjiDao.observeStudiedCount(),
        vocabularyDao.observeStudiedCount(),
        progressDao.observeProgress(),
        favoriteKanji(),
        favoriteVocabulary()
    ) { studiedKanji, studiedVocab, progress, favKanji, favVocab ->
        ProgressSummary(
            studiedKanji = studiedKanji,
            studiedVocabulary = studiedVocab,
            quizCompleted = progress?.quizCompleted ?: 0,
            correct = progress?.quizCorrect ?: 0,
            total = progress?.quizTotal ?: 0,
            favorites = favKanji.size + favVocab.size
        )
    }

    suspend fun ensureSeeded() {
        if (kanjiDao.count() == 0) kanjiDao.insertAll(SeedData.kanji)
        if (vocabularyDao.count() == 0) vocabularyDao.insertAll(SeedData.vocabulary)
        progressDao.insertInitial()
    }

    suspend fun dailyPair(): DailyPair {
        ensureSeeded()
        val kanji = kanjiDao.getAll()
        val vocabulary = vocabularyDao.getAll()
        val dayIndex = LocalDate.now().dayOfYear
        return DailyPair(
            kanji = kanji[dayIndex % kanji.size],
            vocabulary = vocabulary[dayIndex % vocabulary.size]
        )
    }

    suspend fun setKanjiFavorite(character: String, favorite: Boolean) = kanjiDao.setFavorite(character, favorite)
    suspend fun setVocabularyFavorite(id: Int, favorite: Boolean) = vocabularyDao.setFavorite(id, favorite)
    suspend fun markKanjiStudied(character: String) = kanjiDao.markStudied(character)
    suspend fun markVocabularyStudied(id: Int) = vocabularyDao.markStudied(id)
    suspend fun recordQuizAnswer(isCorrect: Boolean) = progressDao.recordQuizAnswer(if (isCorrect) 1 else 0)
    suspend fun allKanjiNow() = kanjiDao.getAll()
    suspend fun allVocabularyNow() = vocabularyDao.getAll()
}

data class DailyPair(
    val kanji: com.example.kanjidaily.data.entity.KanjiEntity,
    val vocabulary: com.example.kanjidaily.data.entity.VocabularyEntity
)

data class ProgressSummary(
    val studiedKanji: Int,
    val studiedVocabulary: Int,
    val quizCompleted: Int,
    val correct: Int,
    val total: Int,
    val favorites: Int
) {
    val accuracy: Int = if (total == 0) 0 else (correct * 100) / total
}
