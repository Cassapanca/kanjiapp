package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun QuizScreen(viewModel: MainViewModel) {
    val state by viewModel.quizState.collectAsState()
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SectionTitle("Quiz")
        StudyCard {
            Text(state.prompt, color = MaterialTheme.colorScheme.primary)
            Text(state.question, fontSize = 56.sp, fontWeight = FontWeight.Bold)
            Text("Score: ${state.score}")
        }
        state.options.forEach { option ->
            val selected = option == state.selectedAnswer
            val label = when {
                selected && state.isCorrect == true -> "$option - Correct"
                selected && state.isCorrect == false -> "$option - Try again"
                else -> option
            }
            OutlinedButton(onClick = { viewModel.answerQuiz(option) }, modifier = Modifier.fillMaxWidth()) { Text(label) }
        }
        if (state.selectedAnswer != null) {
            Button(onClick = viewModel::nextQuestion) { Text("Next question") }
        }
    }
}
