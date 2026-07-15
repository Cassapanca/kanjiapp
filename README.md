# Kanji Daily

Kanji Daily is a small native Android study app built with Kotlin, Jetpack Compose, Room, DataStore, WorkManager, and a Glance app widget.

## Current Status

The app currently includes:

- Home screen with a kanji of the day and vocabulary of the day
- Kanji and vocabulary lists backed by Room
- JLPT filters for All, N5, and N4
- Detail screens for kanji and vocabulary
- Favorites for both kanji and vocabulary
- Multiple-choice quiz with persisted answer stats
- Progress screen for studied items, quiz totals, accuracy, and favorites
- Settings stored with DataStore
- Daily WorkManager job for widget refresh and optional notifications
- Android home-screen widget built with Jetpack Glance

## Requirements

- Android Studio with Android Gradle Plugin support
- JDK 17 selected in Android Studio
- Android SDK 35 installed
- An emulator or physical device running Android 8.0+; Android 13+ is recommended for notification permission testing

This repository does not currently include a Gradle wrapper. Open it from Android Studio, or generate a wrapper from a machine with Gradle installed.

## Build

1. Open Android Studio.
2. Choose **Open**.
3. Select this project folder.
4. Let Gradle sync.
5. Run **Build > Make Project**.

If building from the terminal after adding a wrapper:

```bash
./gradlew :app:assembleDebug
```

## Run

1. Select the `app` run configuration.
2. Choose an emulator or connected device.
3. Press **Run**.

The Room database is seeded on first app launch with sample N5/N4 kanji and vocabulary.

## Widget Testing

1. Install and launch the app once so the database can seed.
2. Long-press the Android launcher home screen.
3. Open **Widgets**.
4. Add the **Kanji Daily** widget.
5. The widget should show the daily kanji, reading, and meaning.

The widget is declared in `AndroidManifest.xml` and configured by `app/src/main/res/xml/kanji_daily_widget_info.xml`.

## Notification Testing

The app creates a notification channel at startup and requests `POST_NOTIFICATIONS` on Android 13+.

The daily worker is scheduled with `ExistingPeriodicWorkPolicy.KEEP`, so relaunching the app does not create duplicate periodic workers. The worker always refreshes the widget; it only posts the daily notification when the settings toggle is enabled and Android notification permission is granted.

For manual testing, use Android Studio's App Inspection tools or trigger WorkManager from a debug build after adding a debug-only entry point.

## Known Limitations

- The project has no Gradle wrapper checked in yet.
- There are no unit or instrumented tests yet.
- Stroke order is a visual placeholder, not real stroke data.
- Notification time is fixed by WorkManager scheduling and is not user-configurable.
- The sample dataset is intentionally small and only covers a subset of N5/N4.

## Recommended Next Improvements

- Add a Gradle wrapper and CI build check.
- Add Room repository tests for seeding, favorites, and quiz progress.
- Add Compose UI tests for navigation and empty states.
- Replace the stroke-order placeholder with real stroke data.
- Add user-configurable notification time.
