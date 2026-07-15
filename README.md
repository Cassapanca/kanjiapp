# Kanji Daily

Kanji Daily is a native Android MVP for daily Japanese, kanji, and vocabulary study. It is inspired by the idea of lightweight daily practice, but uses an original name, UI, data set, icon placeholder, and implementation.

## Stack

- Kotlin
- Jetpack Compose and Material 3
- MVVM with Repository layer
- Room Database
- Coroutines and Flow
- DataStore Preferences
- WorkManager
- Android App Widget with Jetpack Glance
- Gradle Kotlin DSL

## MVP Features

- Home screen with kanji and vocabulary of the day
- Kanji list with JLPT filters for All, N5, and N4
- Vocabulary list with readings, romaji, examples, and translations
- Detail screens for kanji and vocabulary
- Static stroke-order placeholder for kanji details
- Quiz with 4 choices, feedback, score, and persisted answer stats
- Favorites screen for saved kanji and vocabulary
- Progress screen with studied counts, quiz totals, accuracy, and favorites
- Settings screen with notification toggle and preferred JLPT level
- Daily WorkManager job for notification and widget refresh
- Glance home-screen widget showing the daily kanji

## Open in Android Studio

1. Open Android Studio.
2. Choose **Open**.
3. Select this project folder: `Kanji app`.
4. Let Gradle sync the project.

## Run the App

1. Select the `app` run configuration.
2. Choose an emulator or connected Android device.
3. Press **Run**.

The database is seeded on first launch with sample JLPT N5/N4 kanji and vocabulary.

## Widget and Notifications

- The widget is implemented with Jetpack Glance in `com.example.kanjidaily.widget`.
- WorkManager schedules a daily refresh job with a simple default delay.
- Android 13+ notification permission is requested on launch.
- The current settings toggle is persisted and ready for deeper scheduling logic; future work should make the worker respect custom notification time and notification opt-out.

## Project Structure

```text
com.example.kanjidaily
  data
    dao
    entity
    local
    repository
    seed
  notification
  presentation
    components
    navigation
    screens
    viewmodel
  ui.theme
  widget
  worker
```

## Next Steps

- Add real stroke-order data and drawing practice.
- Add spaced repetition scheduling.
- Add search and richer JLPT filters.
- Make notification time user-configurable.
- Add instrumented tests for Room and Compose screens.
- Replace placeholder launcher icon with a polished original brand asset.
