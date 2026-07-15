package com.example.kanjidaily.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kanjidaily.presentation.components.PrimaryActionButton
import com.example.kanjidaily.presentation.components.StudyCard

private data class OnboardingPage(val title: String, val body: String, val glyph: String)

private val onboardingPages = listOf(
    OnboardingPage("Learn Japanese daily", "Build a small study habit with kanji, words and quizzes every day.", "日"),
    OnboardingPage("Study by JLPT level", "Start with N5 and N4 content, then expand your vocabulary over time.", "級"),
    OnboardingPage("Practice with quizzes", "Test your memory with quick multiple-choice questions.", "?"),
    OnboardingPage("Daily widget and reminders", "Keep one Japanese word visible on your Home screen.", "学")
)

@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    var pageIndex by remember { mutableIntStateOf(0) }
    val page = onboardingPages[pageIndex]
    val isLast = pageIndex == onboardingPages.lastIndex

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(22.dp)) {
            Text("Kanji Daily", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            StudyCard {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        page.glyph,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 34.dp),
                        fontSize = 76.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(page.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(page.body, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                onboardingPages.forEachIndexed { index, _ ->
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = if (index == pageIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.height(6.dp).weight(1f)
                    ) {}
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onComplete) { Text("Skip") }
                PrimaryActionButton(
                    text = if (isLast) "Get started" else "Next",
                    onClick = {
                        if (isLast) onComplete() else pageIndex += 1
                    }
                )
            }
        }
    }
}
