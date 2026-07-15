package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kanjidaily.presentation.components.EmptyState
import com.example.kanjidaily.presentation.components.FavoriteButton
import com.example.kanjidaily.presentation.components.JlptChip
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun KanjiDetailScreen(viewModel: MainViewModel, character: String) {
    val item by viewModel.observeKanji(character).collectAsState(initial = null)
    val words by viewModel.vocabulary.collectAsState()
    val relatedWords = words.filter { it.word.contains(character) }.take(4)

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item?.let {
            StudyCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(it.character, fontSize = 96.sp, fontWeight = FontWeight.Bold)
                        Text(it.meaning, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
                    }
                    FavoriteButton(it.isFavorite) { viewModel.toggleKanjiFavorite(it) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    JlptChip(it.jlptLevel)
                    Text("${it.strokes} strokes", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("Onyomi: ${it.onyomi.ifBlank { "-" }}")
                Text("Kunyomi: ${it.kunyomi.ifBlank { "-" }}")
            }

            StudyCard {
                SectionTitle("Example words")
                Text(it.examples, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            StudyCard {
                SectionTitle("Stroke order", "Placeholder for future writing practice.")
                Box(
                    Modifier.fillMaxWidth().height(180.dp).background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.large),
                    contentAlignment = Alignment.Center
                ) {
                    Text(it.character, fontSize = 96.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }

            if (relatedWords.isNotEmpty()) {
                StudyCard {
                    SectionTitle("Related vocabulary")
                    relatedWords.forEach { word ->
                        Text("${word.word} · ${word.reading} · ${word.meaning}")
                    }
                }
            }
        } ?: EmptyState("Kanji not found.", "This study card may no longer be available.")
    }
}

@Composable
fun VocabularyDetailScreen(viewModel: MainViewModel, id: Int) {
    val item by viewModel.observeVocabulary(id).collectAsState(initial = null)
    val kanji by viewModel.kanji.collectAsState()

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item?.let {
            val relatedKanji = kanji.filter { kanjiItem -> it.word.contains(kanjiItem.character) }.take(6)
            StudyCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.weight(1f)) {
                        Text(it.word, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                        Text("${it.reading} · ${it.romaji}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(it.meaning, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                        JlptChip(it.jlptLevel)
                    }
                    FavoriteButton(it.isFavorite) { viewModel.toggleVocabularyFavorite(it) }
                }
            }

            StudyCard {
                SectionTitle("Example")
                Text(it.exampleSentence, style = MaterialTheme.typography.titleMedium)
                Text(it.exampleTranslation, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            if (relatedKanji.isNotEmpty()) {
                StudyCard {
                    SectionTitle("Related kanji")
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        relatedKanji.forEach { kanjiItem ->
                            Text(kanjiItem.character, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } ?: EmptyState("Word not found.", "This vocabulary card may no longer be available.")
    }
}
