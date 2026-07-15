package com.example.kanjidaily.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocabulary")
data class VocabularyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    val reading: String,
    val romaji: String,
    val meaning: String,
    val jlptLevel: String,
    val exampleSentence: String,
    val exampleTranslation: String,
    val isFavorite: Boolean = false,
    val studied: Boolean = false
)
