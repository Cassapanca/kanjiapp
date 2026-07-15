package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kanjidaily.presentation.components.EmptyState
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StatCard
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun ProgressScreen(viewModel: MainViewModel, onStartQuiz: () -> Unit) {
    val progress by viewModel.progress.collectAsState()
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SectionTitle("Progress", "Your study rhythm at a glance.")
        progress?.let {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                StatCard("Quiz completed", it.quizCompleted.toString(), Modifier.weight(1f))
                StatCard("Accuracy", "${it.accuracy}%", Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                StatCard("Kanji studied", it.studiedKanji.toString(), Modifier.weight(1f))
                StatCard("Words studied", it.studiedVocabulary.toString(), Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                StatCard("Correct answers", "${it.correct}/${it.total}", Modifier.weight(1f))
                StatCard("Favorites", it.favorites.toString(), Modifier.weight(1f))
            }
            StudyCard {
                SectionTitle("Study streak")
                Text(if (it.quizCompleted > 0 || it.studiedKanji > 0 || it.studiedVocabulary > 0) "1 day" else "Start today")
                LinearProgressIndicator(progress = { it.accuracy / 100f }, modifier = Modifier.fillMaxWidth())
            }
            StudyCard {
                SectionTitle("Recommended next step")
                Text("Try a 10-question N5 quiz.")
                Button(onClick = onStartQuiz) { Text("Start quiz") }
            }
        } ?: EmptyState("Progress is loading.", "Your stats will appear after a few study actions.")
    }
}
