package com.example.kanjidaily.presentation.screens

import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun FavoritesScreen(viewModel: MainViewModel, navController: NavController) {
    val kanji by viewModel.favoriteKanji.collectAsState()
    val vocabulary by viewModel.favoriteVocabulary.collectAsState()
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Favorites")
        if (kanji.isEmpty() && vocabulary.isEmpty()) Text("No favorites yet.")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(kanji) { item -> KanjiRow(item, { navController.navigate("kanji/${Uri.encode(item.character)}") }, { viewModel.toggleKanjiFavorite(item) }) }
            items(vocabulary) { item -> VocabularyRow(item, { navController.navigate("vocabulary/${item.id}") }, { viewModel.toggleVocabularyFavorite(item) }) }
        }
    }
}
