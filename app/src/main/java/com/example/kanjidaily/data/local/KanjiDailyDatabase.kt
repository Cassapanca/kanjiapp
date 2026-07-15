package com.example.kanjidaily.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kanjidaily.data.dao.KanjiDao
import com.example.kanjidaily.data.dao.ProgressDao
import com.example.kanjidaily.data.dao.VocabularyDao
import com.example.kanjidaily.data.entity.KanjiEntity
import com.example.kanjidaily.data.entity.UserProgressEntity
import com.example.kanjidaily.data.entity.VocabularyEntity

@Database(
    entities = [KanjiEntity::class, VocabularyEntity::class, UserProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KanjiDailyDatabase : RoomDatabase() {
    abstract fun kanjiDao(): KanjiDao
    abstract fun vocabularyDao(): VocabularyDao
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile private var INSTANCE: KanjiDailyDatabase? = null

        fun getDatabase(context: Context): KanjiDailyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    KanjiDailyDatabase::class.java,
                    "kanji_daily.db"
                ).build().also { INSTANCE = it }
            }
    }
}
