package com.example.kanjidaily.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kanji")
data class KanjiEntity(
    @PrimaryKey val character: String,
    val meaning: String,
    val onyomi: String,
    val kunyomi: String,
    val strokes: Int,
    val jlptLevel: String,
    val examples: String,
    val isFavorite: Boolean = false,
    val studied: Boolean = false
)
