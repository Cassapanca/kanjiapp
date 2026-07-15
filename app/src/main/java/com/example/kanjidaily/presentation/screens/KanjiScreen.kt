package com.example.kanjidaily.presentation.screens

import android.net.Uri
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
import com.example.kanjidaily.presentation.components.KanjiCard
import com.example.kanjidaily.presentation.components.LevelFilters
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun KanjiScreen(viewModel: MainViewModel, navController: NavController) {
    val currentLevel by viewModel.currentLevel.collectAsState()
    val items by viewModel.kanji.collectAsState()
    var query by remember { mutableStateOf("") }
    val filtered = items.filter {
        query.isBlank() ||
            it.character.contains(query.trim(), ignoreCase = true) ||
            it.meaning.contains(query.trim(), ignoreCase = true)
    }

    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Kanji", "Search meanings, readings and study cards.")
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search kanji or meaning") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        LevelFilters(currentLevel, viewModel::setLevel)
        if (filtered.isEmpty()) {
            EmptyState("No kanji found.", "Try another search or switch JLPT level.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(filtered, key = { it.character }) { item ->
                    KanjiCard(
                        item = item,
                        onClick = { navController.navigate("kanji/${Uri.encode(item.character)}") },
                        onFavorite = { viewModel.toggleKanjiFavorite(item) }
                    )
                }
            }
        }
    }
}
