# Kanji Daily

Kanji Daily is a small native Android app for daily Japanese practice.

Payoff: **Learn Japanese one card at a time**

## Current Status

The app includes:

- First-run onboarding stored with DataStore
- Today screen with daily kanji and daily vocabulary
- Kanji and vocabulary lists backed by Room
- Local search and JLPT filters for All, N5, and N4
- Detail screens for kanji and vocabulary
- Favorites for both kanji and vocabulary
- 10-question quiz flow with type and level selection
- Progress dashboard with simple stats and study streak placeholder
- Settings stored with DataStore
- Daily WorkManager job for widget refresh and optional reminders
- Android home-screen widget built with Jetpack Glance
- Android SplashScreen API and adaptive icon placeholder

## Product Polish Update

Branding:

- App name: Kanji Daily
- Payoff: Learn Japanese one card at a time
- Tone: warm, minimal, educational, friendly but not childish
- Visual identity: original warm study-card style; no copied mascot, icon, layout, text, or assets from other apps

Palette:

- Light background: warm cream
- Light primary: soft coral red
- Light secondary: muted orange
- Light cards: white
- Light text: very dark gray
- Dark background: very dark blue/black
- Dark cards: elegant dark gray
- Dark primary: soft red/orange
- Dark text: white and light gray

UI/UX updates:

- Onboarding introduces the study habit, JLPT levels, quizzes, and widget/reminders.
- Today screen now has complete daily kanji and word cards plus quick actions.
- Bottom navigation is focused on Today, Kanji, Words, Quiz, and Progress.
- Favorites and Settings are available from top actions.
- Lists include local search, filters, reusable cards, and empty states.
- Quiz has setup, active-question feedback, progress, and result screens.
- Settings includes onboarding reset for testing.

## Requirements

- Android Studio with Android Gradle Plugin support
- JDK 17 selected in Android Studio
- Android SDK 35 installed
- Emulator or physical device running Android 8.0+
- Android 13+ device/emulator for notification permission testing

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

## Test Onboarding

1. Launch the app for the first time.
2. Complete or skip onboarding.
3. Open Settings.
4. Tap **Reset onboarding**.
5. Relaunch or return to the app; onboarding should appear again.

## Test Widget

1. Install and launch the app once so the database can seed.
2. Long-press the Android launcher home screen.
3. Open **Widgets**.
4. Add the **Kanji Daily** widget.
5. Confirm it shows the app name, daily kanji, reading, and meaning.
6. Tap the widget to open the app.

The widget is declared in `AndroidManifest.xml` and configured by `app/src/main/res/xml/kanji_daily_widget_info.xml`.

## Test Notifications

The app creates a notification channel at startup and requests `POST_NOTIFICATIONS` on Android 13+.

The daily worker:

- Uses `ExistingPeriodicWorkPolicy.KEEP`
- Refreshes the widget
- Checks the DataStore notification toggle
- Posts a notification only when permission is granted
- Opens the app when the notification is tapped

For manual testing, use Android Studio App Inspection tools or add a debug-only trigger for WorkManager.

## Known Limitations

- No Gradle wrapper is checked in yet.
- No unit or instrumented tests are included yet.
- Stroke order is a visual placeholder, not real stroke data.
- Reminder time is a settings placeholder and is not scheduled dynamically yet.
- Study streak is a simple placeholder, not a full calendar-based streak system.
- Sample dataset is intentionally small and only covers a subset of N5/N4.
- Widget size variants are minimal; only one simple layout is implemented.

## Roadmap

1. Real stroke order
2. Audio pronunciation
3. SRS/spaced repetition
4. JLPT N3/N2/N1
5. Account and cloud sync
6. Customizable theme
7. Multiple widgets
8. Freemium monetization

## Recommended Engineering Next Steps

- Add a Gradle wrapper and CI build check.
- Add Room tests for seeding, favorites, and quiz progress.
- Add Compose UI tests for onboarding, navigation, search, and quiz states.
- Add a debug-only WorkManager trigger for notification testing.
