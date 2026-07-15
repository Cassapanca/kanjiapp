package com.example.kanjidaily.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kanjidaily.data.entity.KanjiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KanjiDao {
    @Query("SELECT * FROM kanji ORDER BY jlptLevel, character")
    fun observeAll(): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji WHERE jlptLevel = :level ORDER BY character")
    fun observeByLevel(level: String): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji WHERE isFavorite = 1 ORDER BY character")
    fun observeFavorites(): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji WHERE character = :character LIMIT 1")
    fun observeKanji(character: String): Flow<KanjiEntity?>

    @Query("SELECT * FROM kanji")
    suspend fun getAll(): List<KanjiEntity>

    @Query("SELECT COUNT(*) FROM kanji")
    suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM kanji WHERE studied = 1")
    fun observeStudiedCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<KanjiEntity>)

    @Query("UPDATE kanji SET isFavorite = :favorite WHERE character = :character")
    suspend fun setFavorite(character: String, favorite: Boolean)

    @Query("UPDATE kanji SET studied = 1 WHERE character = :character")
    suspend fun markStudied(character: String)
}
