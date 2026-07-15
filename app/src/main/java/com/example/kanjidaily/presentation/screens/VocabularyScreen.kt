package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kanjidaily.presentation.components.EmptyState
import com.example.kanjidaily.presentation.components.LevelFilters
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.VocabularyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun VocabularyScreen(viewModel: MainViewModel, navController: NavController) {
    val currentLevel by viewModel.currentLevel.collectAsState()
    val items by viewModel.vocabulary.collectAsState()
    var query by remember { mutableStateOf("") }
    val needle = query.trim()
    val filtered = items.filter {
        needle.isBlank() ||
            it.word.contains(needle, ignoreCase = true) ||
            it.reading.contains(needle, ignoreCase = true) ||
            it.romaji.contains(needle, ignoreCase = true) ||
            it.meaning.contains(needle, ignoreCase = true)
    }

    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Words", "Search Japanese, kana, romaji or meaning.")
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search words") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        LevelFilters(currentLevel, viewModel::setLevel)
        if (filtered.isEmpty()) {
            EmptyState("No words found.", "Try another search or switch JLPT level.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(filtered, key = { it.id }) { item ->
                    VocabularyCard(
                        item = item,
                        onClick = { navController.navigate("vocabulary/${item.id}") },
                        onFavorite = { viewModel.toggleVocabularyFavorite(item) }
                    )
                }
            }
        }
    }
}
