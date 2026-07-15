package com.example.kanjidaily.presentation.navigation

import android.net.Uri
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kanjidaily.presentation.components.AppBottomBar
import com.example.kanjidaily.presentation.components.AppScaffold
import com.example.kanjidaily.presentation.components.LoadingState
import com.example.kanjidaily.presentation.screens.FavoritesScreen
import com.example.kanjidaily.presentation.screens.HomeScreen
import com.example.kanjidaily.presentation.screens.KanjiDetailScreen
import com.example.kanjidaily.presentation.screens.KanjiScreen
import com.example.kanjidaily.presentation.screens.OnboardingScreen
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
    val settings by mainViewModel.settings.collectAsState()
    val settingsLoaded by mainViewModel.settingsLoaded.collectAsState()

    if (!settingsLoaded) {
        LoadingState("Opening Kanji Daily...")
        return
    }

    if (!settings.onboardingCompleted) {
        OnboardingScreen(onComplete = mainViewModel::completeOnboarding)
        return
    }

    val tabs = listOf(Screen.Home, Screen.Kanji, Screen.Vocabulary, Screen.Quiz, Screen.Progress)
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val title = screenTitle(currentRoute)

    AppScaffold(
        title = title,
        actions = {
            IconButton(onClick = { navController.navigate(Screen.Favorites.route) { launchSingleTop = true } }) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Open favorites")
            }
            IconButton(onClick = { navController.navigate(Screen.Settings.route) { launchSingleTop = true } }) {
                Icon(Icons.Outlined.Settings, contentDescription = "Open settings")
            }
        },
        bottomBar = {
            AppBottomBar {
                tabs.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(screen.label) },
                        icon = { Icon(screen.icon, contentDescription = screen.label) }
                    )
                }
            }
        }
    ) { contentModifier ->
        NavHost(navController, startDestination = Screen.Home.route, modifier = contentModifier) {
            composable(Screen.Home.route) { HomeScreen(mainViewModel, navController) }
            composable(Screen.Kanji.route) { KanjiScreen(mainViewModel, navController) }
            composable(Screen.Vocabulary.route) { VocabularyScreen(mainViewModel, navController) }
            composable(Screen.Quiz.route) { QuizScreen(mainViewModel, onHome = { navController.navigate(Screen.Home.route) }) }
            composable(Screen.Progress.route) { ProgressScreen(mainViewModel, onStartQuiz = { navController.navigate(Screen.Quiz.route) }) }
            composable(Screen.Favorites.route) { FavoritesScreen(mainViewModel, navController) }
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

private fun screenTitle(route: String?): String = when {
    route == Screen.Home.route -> "Today"
    route == Screen.Kanji.route -> "Kanji"
    route == Screen.Vocabulary.route -> "Words"
    route == Screen.Quiz.route -> "Quiz"
    route == Screen.Progress.route -> "Progress"
    route == Screen.Favorites.route -> "Favorites"
    route == Screen.Settings.route -> "Settings"
    route?.startsWith("kanji/") == true -> "Kanji detail"
    route?.startsWith("vocabulary/") == true -> "Word detail"
    else -> "Kanji Daily"
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen("home", "Today", Icons.Outlined.Home)
    data object Kanji : Screen("kanji", "Kanji", Icons.AutoMirrored.Outlined.List)
    data object Vocabulary : Screen("vocabulary", "Words", Icons.Outlined.Edit)
    data object Quiz : Screen("quiz", "Quiz", Icons.Outlined.TaskAlt)
    data object Progress : Screen("progress", "Progress", Icons.Outlined.BarChart)
    data object Favorites : Screen("favorites", "Saved", Icons.Outlined.FavoriteBorder)
    data object Settings : Screen("settings", "Settings", Icons.Outlined.Settings)
}
