package com.example.kanjidaily.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kanjidaily.data.entity.UserProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun observeProgress(): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitial(progress: UserProgressEntity = UserProgressEntity())

    @Query("UPDATE user_progress SET quizCorrect = quizCorrect + :correct, quizTotal = quizTotal + 1, quizCompleted = quizCompleted + 1 WHERE id = 1")
    suspend fun recordQuizAnswer(correct: Int)
}
