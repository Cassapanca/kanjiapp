package com.example.kanjidaily

import android.app.Application
import com.example.kanjidaily.data.local.KanjiDailyDatabase
import com.example.kanjidaily.data.repository.KanjiDailyRepository
import com.example.kanjidaily.notification.NotificationHelper
import com.example.kanjidaily.worker.DailyWorkScheduler

class KanjiDailyApplication : Application() {
    val database by lazy { KanjiDailyDatabase.getDatabase(this) }
    val repository by lazy { KanjiDailyRepository(database.kanjiDao(), database.vocabularyDao(), database.progressDao()) }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannel(this)
        DailyWorkScheduler.scheduleDailyWork(this)
    }
}
