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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kanjidaily.presentation.components.EmptyState
import com.example.kanjidaily.presentation.components.LevelFilters
import com.example.kanjidaily.presentation.components.QuizOptionButton
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel
import com.example.kanjidaily.presentation.viewmodel.QuizMode

@Composable
fun QuizScreen(viewModel: MainViewModel, onHome: () -> Unit) {
    val state by viewModel.quizState.collectAsState()
    var selectedMode by remember { mutableStateOf(QuizMode.Mixed) }
    var selectedLevel by remember { mutableStateOf("All") }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        when {
            !state.quizStarted -> QuizSetup(
                selectedMode = selectedMode,
                selectedLevel = selectedLevel,
                onModeSelected = { selectedMode = it },
                onLevelSelected = { selectedLevel = it },
                onStart = { viewModel.startQuiz(selectedMode, selectedLevel) }
            )
            state.quizFinished -> QuizResult(
                correct = state.score,
                total = state.totalQuestions,
                onRetry = { viewModel.startQuiz(state.mode, state.level) },
                onHome = onHome
            )
            state.options.isEmpty() -> EmptyState(state.prompt, state.question)
            else -> ActiveQuiz(state = state, onAnswer = viewModel::answerQuiz, onNext = viewModel::nextQuestion)
        }
    }
}

@Composable
private fun QuizSetup(
    selectedMode: QuizMode,
    selectedLevel: String,
    onModeSelected: (QuizMode) -> Unit,
    onLevelSelected: (String) -> Unit,
    onStart: () -> Unit
) {
    SectionTitle("Quick quiz", "Choose a focus and practice 10 questions.")
    StudyCard {
        Text("Quiz type", fontWeight = FontWeight.SemiBold)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            QuizMode.entries.forEach { mode ->
                FilterChip(selected = selectedMode == mode, onClick = { onModeSelected(mode) }, label = { Text(mode.label) })
            }
        }
        Text("JLPT level", fontWeight = FontWeight.SemiBold)
        LevelFilters(selectedLevel, onLevelSelected)
        Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) {
            Text("Start quiz")
        }
    }
}

@Composable
private fun ActiveQuiz(
    state: com.example.kanjidaily.presentation.viewmodel.QuizUiState,
    onAnswer: (String) -> Unit,
    onNext: () -> Unit
) {
    SectionTitle("Question ${state.questionIndex}/${state.totalQuestions}", "Score: ${state.score}")
    LinearProgressIndicator(
        progress = { (state.questionIndex - 1) / state.totalQuestions.toFloat() },
        modifier = Modifier.fillMaxWidth()
    )
    StudyCard {
        Text(state.prompt, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
        Text(state.question, fontSize = 52.sp, fontWeight = FontWeight.Bold)
    }
    state.options.forEach { option ->
        QuizOptionButton(
            text = option,
            selected = option == state.selectedAnswer,
            correct = if (option == state.selectedAnswer) state.isCorrect else null,
            onClick = { onAnswer(option) }
        )
    }
    state.selectedAnswer?.let {
        Text(
            if (state.isCorrect == true) "Nice work!" else "Keep practicing. Correct answer: ${state.correctAnswer}",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) {
            Text(if (state.questionIndex == state.totalQuestions) "See result" else "Next")
        }
    }
}

@Composable
private fun QuizResult(correct: Int, total: Int, onRetry: () -> Unit, onHome: () -> Unit) {
    val percent = if (total == 0) 0 else (correct * 100) / total
    SectionTitle("Quiz result", "Nice work. Keep the habit going.")
    StudyCard {
        Text("$correct/$total", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        Text("$percent% accuracy", style = MaterialTheme.typography.titleMedium)
        Text(if (percent >= 80) "Strong session." else "A little practice every day adds up.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onRetry, modifier = Modifier.weight(1f)) { Text("Retry") }
            OutlinedButton(onClick = onHome, modifier = Modifier.weight(1f)) { Text("Home") }
        }
    }
}
