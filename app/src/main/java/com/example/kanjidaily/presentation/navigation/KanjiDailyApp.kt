package com.example.kanjidaily.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kanjidaily.presentation.screens.FavoritesScreen
import com.example.kanjidaily.presentation.screens.HomeScreen
import com.example.kanjidaily.presentation.screens.KanjiDetailScreen
import com.example.kanjidaily.presentation.screens.KanjiScreen
import com.example.kanjidaily.presentation.screens.ProgressScreen
import com.example.kanjidaily.presentation.screens.QuizScreen
import com.example.kanjidaily.presentation.screens.SettingsScreen
import com.example.kanjidaily.presentation.screens.VocabularyDetailScreen
import com.example.kanjidaily.presentation.screens.VocabularyScreen
import com.example.kanjidaily.presentation.viewmodel.MainViewModel
import com.example.kanjidaily.presentation.viewmodel.SettingsViewModel

@Composable
fun KanjiDailyApp(factory: ViewModelProvider.Factory) {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel(factory = factory)
    val settingsViewModel: SettingsViewModel = viewModel(factory = factory)
    val tabs = listOf(Screen.Home, Screen.Kanji, Screen.Vocabulary, Screen.Quiz, Screen.Favorites, Screen.Progress, Screen.Settings)
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(screen.label) },
                        icon = { Text(screen.icon) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(padding)) {
            composable(Screen.Home.route) { HomeScreen(mainViewModel, navController) }
            composable(Screen.Kanji.route) { KanjiScreen(mainViewModel, navController) }
            composable(Screen.Vocabulary.route) { VocabularyScreen(mainViewModel, navController) }
            composable(Screen.Quiz.route) { QuizScreen(mainViewModel) }
            composable(Screen.Favorites.route) { FavoritesScreen(mainViewModel, navController) }
            composable(Screen.Progress.route) { ProgressScreen(mainViewModel) }
            composable(Screen.Settings.route) { SettingsScreen(settingsViewModel) }
            composable("kanji/{character}", listOf(navArgument("character") { type = NavType.StringType })) {
                KanjiDetailScreen(mainViewModel, Uri.decode(it.arguments?.getString("character").orEmpty()))
            }
            composable("vocabulary/{id}", listOf(navArgument("id") { type = NavType.IntType })) {
                VocabularyDetailScreen(mainViewModel, it.arguments?.getInt("id") ?: 0)
            }
        }
    }
}

sealed class Screen(val route: String, val label: String, val icon: String) {
    data object Home : Screen("home", "Home", "日")
    data object Kanji : Screen("kanji", "Kanji", "漢")
    data object Vocabulary : Screen("vocabulary", "Words", "語")
    data object Quiz : Screen("quiz", "Quiz", "?")
    data object Favorites : Screen("favorites", "Saved", "★")
    data object Progress : Screen("progress", "Progress", "%")
    data object Settings : Screen("settings", "Settings", "⚙")
}
