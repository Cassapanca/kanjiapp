package com.example.kanjidaily.presentation.screens

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kanjidaily.presentation.components.EmptyState
import com.example.kanjidaily.presentation.components.KanjiCard
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.VocabularyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun FavoritesScreen(viewModel: MainViewModel, navController: NavController) {
    val kanji by viewModel.favoriteKanji.collectAsState()
    val vocabulary by viewModel.favoriteVocabulary.collectAsState()
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (kanji.isEmpty() && vocabulary.isEmpty()) {
            EmptyState("No favorites yet.", "Your saved study cards will appear here.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                item { SectionTitle("Favorite Kanji") }
                if (kanji.isEmpty()) {
                    item { EmptyState("No saved kanji.", "Tap the heart on any kanji card to save it.") }
                } else {
                    items(kanji, key = { it.character }) { item ->
                        KanjiCard(
                            item = item,
                            onClick = { navController.navigate("kanji/${Uri.encode(item.character)}") },
                            onFavorite = { viewModel.toggleKanjiFavorite(item) }
                        )
                    }
                }
                item { SectionTitle("Favorite Words") }
                if (vocabulary.isEmpty()) {
                    item { EmptyState("No saved words.", "Tap the heart on any word card to save it.") }
                } else {
                    items(vocabulary, key = { it.id }) { item ->
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
}
