package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.example.kanjidaily.data.entity.KanjiEntity
import com.example.kanjidaily.presentation.components.FavoriteButton
import com.example.kanjidaily.presentation.components.LevelFilters
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.MainViewModel

@Composable
fun KanjiScreen(viewModel: MainViewModel, navController: NavController) {
    var level by remember { mutableStateOf("All") }
    val items by viewModel.kanji.collectAsState()
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionTitle("Kanji")
        LevelFilters(level) { level = it; viewModel.setLevel(it) }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item -> KanjiRow(item, { navController.navigate("kanji/${item.character}") }, { viewModel.toggleKanjiFavorite(item) }) }
        }
    }
}

@Composable
fun KanjiRow(item: KanjiEntity, onClick: () -> Unit, onFavorite: () -> Unit) {
    StudyCard(Modifier.clickable(onClick = onClick)) {
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Text(item.character, fontSize = 42.sp, fontWeight = FontWeight.Bold)
            Column(Modifier.weight(1f)) {
                Text(item.meaning, fontWeight = FontWeight.SemiBold)
                Text("On: ${item.onyomi} · Kun: ${item.kunyomi}")
                Text("${item.strokes} strokes · ${item.jlptLevel}")
            }
        }
        FavoriteButton(item.isFavorite, onFavorite)
    }
}
