package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.kanjidaily.presentation.components.FavoriteButton
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun KanjiDetailScreen(viewModel: MainViewModel, character: String) {
    val item by viewModel.observeKanji(character).collectAsState(initial = null)
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item?.let {
            SectionTitle("Kanji detail")
            StudyCard {
                Text(it.character, fontSize = 82.sp, fontWeight = FontWeight.Bold)
                Text(it.meaning, style = MaterialTheme.typography.titleLarge)
                Text("Onyomi: ${it.onyomi}")
                Text("Kunyomi: ${it.kunyomi}")
                Text("Strokes: ${it.strokes}")
                Text("JLPT: ${it.jlptLevel}")
                Text("Examples: ${it.examples}")
                FavoriteButton(it.isFavorite) { viewModel.toggleKanjiFavorite(it) }
            }
            StudyCard {
                Text("Stroke order placeholder", fontWeight = FontWeight.SemiBold)
                Box(Modifier.fillMaxWidth().height(160.dp).background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
                    Text(it.character, fontSize = 88.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
        } ?: Text("Kanji not found.")
    }
}

@Composable
fun VocabularyDetailScreen(viewModel: MainViewModel, id: Int) {
    val item by viewModel.observeVocabulary(id).collectAsState(initial = null)
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item?.let {
            SectionTitle("Vocabulary detail")
            StudyCard {
                Text(it.word, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Text("${it.reading} · ${it.romaji}")
                Text(it.meaning, style = MaterialTheme.typography.titleLarge)
                Text("JLPT: ${it.jlptLevel}")
                Text(it.exampleSentence)
                Text(it.exampleTranslation)
                FavoriteButton(it.isFavorite) { viewModel.toggleVocabularyFavorite(it) }
            }
        } ?: Text("Vocabulary not found.")
    }
}
