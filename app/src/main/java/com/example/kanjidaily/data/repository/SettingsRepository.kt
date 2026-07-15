package com.example.kanjidaily.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingsRepository(private val context: Context) {
    private val notificationsKey = booleanPreferencesKey("daily_notifications")
    private val jlptKey = stringPreferencesKey("preferred_jlpt")
    private val onboardingCompletedKey = booleanPreferencesKey("onboarding_completed")

    val settings = context.dataStore.data.map {
        AppSettings(
            notificationsEnabled = it[notificationsKey] ?: true,
            preferredJlpt = it[jlptKey] ?: "All",
            onboardingCompleted = it[onboardingCompletedKey] ?: false
        )
    }

    suspend fun setNotifications(enabled: Boolean) {
        context.dataStore.edit { it[notificationsKey] = enabled }
    }

    suspend fun setJlpt(level: String) {
        context.dataStore.edit { it[jlptKey] = level }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { it[onboardingCompletedKey] = completed }
    }
}

data class AppSettings(
    val notificationsEnabled: Boolean,
    val preferredJlpt: String,
    val onboardingCompleted: Boolean
)
