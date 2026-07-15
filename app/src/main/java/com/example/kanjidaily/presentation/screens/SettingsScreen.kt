package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kanjidaily.presentation.components.SectionTitle
import com.example.kanjidaily.presentation.components.StudyCard
import com.example.kanjidaily.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val settings by viewModel.settings.collectAsState()
    Column(Modifier.fillMaxSize().padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        SectionTitle("Settings")
        StudyCard {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Daily notifications", Modifier.weight(1f))
                Switch(checked = settings.notificationsEnabled, onCheckedChange = viewModel::setNotifications)
            }
            Text("Preferred JLPT level")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("All", "N5", "N4").forEach {
                    FilterChip(selected = settings.preferredJlpt == it, onClick = { viewModel.setJlpt(it) }, label = { Text(it) })
                }
            }
            Text("Kanji Daily MVP 1.0")
            Text("A native study app for daily Japanese practice.")
        }
    }
}
