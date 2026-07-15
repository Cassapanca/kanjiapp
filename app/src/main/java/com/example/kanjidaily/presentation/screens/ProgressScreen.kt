package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun ProgressScreen(viewModel: MainViewModel) {
    val progress by viewModel.progress.collectAsState()
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SectionTitle("Progress")
        progress?.let {
            StudyCard {
                Text("Kanji studied: ${it.studiedKanji}")
                Text("Vocabulary studied: ${it.studiedVocabulary}")
                Text("Quiz completed: ${it.quizCompleted}")
                Text("Correct answers: ${it.correct}/${it.total}")
                Text("Accuracy: ${it.accuracy}%")
                LinearProgressIndicator(progress = { it.accuracy / 100f })
                Text("Favorites total: ${it.favorites}")
            }
        } ?: Text("Progress is loading.")
    }
}
