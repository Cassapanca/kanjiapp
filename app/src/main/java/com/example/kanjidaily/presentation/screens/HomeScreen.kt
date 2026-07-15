package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import com.example.kanjidaily.presentation.components.FavoriteButton
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavController) {
    val daily by viewModel.daily.collectAsState()
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionTitle("Kanji Daily")
        daily?.let { pair ->
            StudyCard {
                Text("Kanji of the day", color = MaterialTheme.colorScheme.primary)
                Text(pair.kanji.character, fontSize = 72.sp, fontWeight = FontWeight.Bold)
                Text(pair.kanji.meaning, style = MaterialTheme.typography.titleMedium)
                Text("${pair.kanji.onyomi} / ${pair.kanji.kunyomi} · ${pair.kanji.jlptLevel}")
                FavoriteButton(pair.kanji.isFavorite) { viewModel.toggleKanjiFavorite(pair.kanji) }
            }
            StudyCard {
                Text("Vocabulary of the day", color = MaterialTheme.colorScheme.primary)
                Text(pair.vocabulary.word, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Text("${pair.vocabulary.reading} · ${pair.vocabulary.romaji}")
                Text("${pair.vocabulary.meaning} · ${pair.vocabulary.jlptLevel}")
                FavoriteButton(pair.vocabulary.isFavorite) { viewModel.toggleVocabularyFavorite(pair.vocabulary) }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = { navController.navigate("quiz") }) { Text("Start quiz") }
                    OutlinedButton(onClick = { navController.navigate("vocabulary") }) { Text("Browse all") }
                }
            }
        } ?: Text("Preparing your daily study card...")
    }
}
