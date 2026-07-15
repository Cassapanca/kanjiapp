package com.example.kanjidaily.presentation.screens

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kanjidaily.presentation.components.EmptyState
import com.example.kanjidaily.presentation.components.FavoriteButton
import com.example.kanjidaily.presentation.components.JlptChip
import com.example.kanjidaily.presentation.components.LoadingState
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavController) {
    val daily by viewModel.daily.collectAsState()
    val settings by viewModel.settings.collectAsState()

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle(
            text = "Today's Japanese",
            subtitle = "Learn Japanese one card at a time."
        )
        JlptChip(level = "Preferred ${settings.preferredJlpt}")

        daily?.let { pair ->
            StudyCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Daily kanji", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    JlptChip(pair.kanji.jlptLevel)
                }
                Text(pair.kanji.character, fontSize = 86.sp, fontWeight = FontWeight.Bold)
                Text(pair.kanji.meaning, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text("On ${pair.kanji.onyomi.ifBlank { "-" }} · Kun ${pair.kanji.kunyomi.ifBlank { "-" }}")
                Text("${pair.kanji.strokes} strokes", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FavoriteButton(pair.kanji.isFavorite) { viewModel.toggleKanjiFavorite(pair.kanji) }
                    TextButton(onClick = { navController.navigate("kanji/${Uri.encode(pair.kanji.character)}") }) {
                        Text("View details")
                    }
                }
            }

            StudyCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Daily word", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    JlptChip(pair.vocabulary.jlptLevel)
                }
                Text(pair.vocabulary.word, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Text("${pair.vocabulary.reading} · ${pair.vocabulary.romaji}")
                Text(pair.vocabulary.meaning, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(pair.vocabulary.exampleSentence, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    FavoriteButton(pair.vocabulary.isFavorite) { viewModel.toggleVocabularyFavorite(pair.vocabulary) }
                    TextButton(onClick = { navController.navigate("vocabulary/${pair.vocabulary.id}") }) {
                        Text("View details")
                    }
                }
            }

            SectionTitle("Quick actions", "Study a little every day.")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { navController.navigate("quiz") }, modifier = Modifier.weight(1f)) { Text("Start quiz") }
                OutlinedButton(onClick = { navController.navigate("progress") }, modifier = Modifier.weight(1f)) { Text("Progress") }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { navController.navigate("kanji") }, modifier = Modifier.weight(1f)) { Text("Browse kanji") }
                OutlinedButton(onClick = { navController.navigate("vocabulary") }, modifier = Modifier.weight(1f)) { Text("Browse words") }
            }
        } ?: run {
            LoadingState("Preparing your daily study cards...")
            EmptyState("Your daily kanji is almost ready.", "The sample database is seeded automatically on first launch.")
        }
    }
}
