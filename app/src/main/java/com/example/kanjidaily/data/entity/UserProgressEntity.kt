package com.example.kanjidaily.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val quizCorrect: Int = 0,
    val quizTotal: Int = 0,
    val quizCompleted: Int = 0
)
