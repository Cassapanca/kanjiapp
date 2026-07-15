package com.example.kanjidaily.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kanjidaily.data.repository.KanjiDailyRepository
import com.example.kanjidaily.data.repository.SettingsRepository

class ViewModelFactory(
    private val repository: KanjiDailyRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    private val settingsRepository by lazy { SettingsRepository(context.applicationContext) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository, settingsRepository) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel(settingsRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}
