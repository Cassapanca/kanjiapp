package com.example.kanjidaily.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjidaily.data.repository.AppSettings
import com.example.kanjidaily.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    val settings = repository.settings.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings(true, "All", false))

    fun setNotifications(enabled: Boolean) = viewModelScope.launch { repository.setNotifications(enabled) }
    fun setJlpt(level: String) = viewModelScope.launch { repository.setJlpt(level) }
    fun resetOnboarding() = viewModelScope.launch { repository.setOnboardingCompleted(false) }
}
