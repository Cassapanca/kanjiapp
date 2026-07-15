package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kanjidaily.data.entity.VocabularyEntity
import com.example.kanjidaily.presentation.components.FavoriteButton
import com.example.kanjidaily.presentation.components.LevelFilters
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun VocabularyScreen(viewModel: MainViewModel, navController: NavController) {
    val currentLevel by viewModel.currentLevel.collectAsState()
    val items by viewModel.vocabulary.collectAsState()
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Vocabulary")
        LevelFilters(currentLevel, viewModel::setLevel)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item -> VocabularyRow(item, { navController.navigate("vocabulary/${item.id}") }, { viewModel.toggleVocabularyFavorite(item) }) }
        }
    }
}

@Composable
fun VocabularyRow(item: VocabularyEntity, onClick: () -> Unit, onFavorite: () -> Unit) {
    StudyCard(Modifier.clickable(onClick = onClick)) {
        Text(item.word, style = androidx.compose.material3.MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("${item.reading} · ${item.romaji}")
        Text("${item.meaning} · ${item.jlptLevel}")
        Text(item.exampleSentence)
        Text(item.exampleTranslation)
        FavoriteButton(item.isFavorite, onFavorite)
    }
}
