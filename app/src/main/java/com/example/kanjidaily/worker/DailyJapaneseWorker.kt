package com.example.kanjidaily.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.glance.appwidget.updateAll
import com.example.kanjidaily.data.local.KanjiDailyDatabase
import com.example.kanjidaily.data.repository.KanjiDailyRepository
import com.example.kanjidaily.notification.NotificationHelper
import com.example.kanjidaily.widget.KanjiDailyWidget
import java.util.concurrent.TimeUnit

class DailyJapaneseWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val db = KanjiDailyDatabase.getDatabase(applicationContext)
        val repository = KanjiDailyRepository(db.kanjiDao(), db.vocabularyDao(), db.progressDao())
        val daily = repository.dailyPair()
        NotificationHelper.showDailyNotification(applicationContext, "${daily.vocabulary.word} (${daily.vocabulary.reading}) - ${daily.vocabulary.meaning}")
        KanjiDailyWidget.updateAll(applicationContext)
        return Result.success()
    }
}

object DailyWorkScheduler {
    fun scheduleDailyWork(context: Context) {
        val request = PeriodicWorkRequestBuilder<DailyJapaneseWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(6, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_japanese_refresh",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}
