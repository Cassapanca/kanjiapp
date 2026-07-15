package com.example.kanjidaily.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kanjidaily.data.entity.VocabularyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {
    @Query("SELECT * FROM vocabulary ORDER BY jlptLevel, word")
    fun observeAll(): Flow<List<VocabularyEntity>>

    @Query("SELECT * FROM vocabulary WHERE jlptLevel = :level ORDER BY word")
    fun observeByLevel(level: String): Flow<List<VocabularyEntity>>

    @Query("SELECT * FROM vocabulary WHERE isFavorite = 1 ORDER BY word")
    fun observeFavorites(): Flow<List<VocabularyEntity>>

    @Query("SELECT * FROM vocabulary WHERE id = :id LIMIT 1")
    fun observeVocabulary(id: Int): Flow<VocabularyEntity?>

    @Query("SELECT * FROM vocabulary")
    suspend fun getAll(): List<VocabularyEntity>

    @Query("SELECT COUNT(*) FROM vocabulary")
    suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM vocabulary WHERE studied = 1")
    fun observeStudiedCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<VocabularyEntity>)

    @Query("UPDATE vocabulary SET isFavorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: Int, favorite: Boolean)

    @Query("UPDATE vocabulary SET studied = 1 WHERE id = :id")
    suspend fun markStudied(id: Int)
}
