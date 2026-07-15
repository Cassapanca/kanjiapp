package com.example.kanjidaily.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kanjidaily.data.local.KanjiDailyDatabase
import com.example.kanjidaily.data.repository.KanjiDailyRepository

class KanjiDailyWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = KanjiDailyWidget
}

object KanjiDailyWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val db = KanjiDailyDatabase.getDatabase(context)
        val daily = KanjiDailyRepository(db.kanjiDao(), db.vocabularyDao(), db.progressDao()).dailyPair()
        provideContent {
            WidgetContent(
                kanji = daily.kanji.character,
                reading = daily.vocabulary.reading,
                meaning = daily.vocabulary.meaning
            )
        }
    }

    @Composable
    private fun WidgetContent(kanji: String, reading: String, meaning: String) {
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .background(ColorProvider(Color(0xFFFFF7F0)))
                .padding(14.dp)
        ) {
            Text("Kanji Daily", style = TextStyle(fontWeight = FontWeight.Bold))
            Text(kanji, style = TextStyle(fontWeight = FontWeight.Bold))
            Text(reading)
            Text(meaning)
        }
    }
}
