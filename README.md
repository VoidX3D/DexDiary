# DexDiary

DexDiary is a free and open-source Android diary app focused on daily writing consistency, streaks, points, unlockables, and reflective insights.

## Status

- Active development
- Single-module Android app (`:app`)
- Build validation is done through GitHub Actions (Ubuntu runner)

## Core Features

- Daily diary entries with mood support
- Streak and points progression
- In-app shop (streak freeze, double points, premium unlocks)
- Insights surface with streak analytics and AI-style summary/vibe placeholders
- Notification settings and scheduled reminder flow
- Material 3 expressive UI with optional Reduce Motion mode

## Tech Stack

- Kotlin + Jetpack Compose
- Room
- DataStore Preferences
- Hilt (DI)
- Coroutines / Flow
- Gradle Kotlin DSL

## Repository Structure

- `app/src/main/java/com/easylife/diary/di`
- `app/src/main/java/com/easylife/diary/data`
- `app/src/main/java/com/easylife/diary/domain`
- `app/src/main/java/com/easylife/diary/presentation`

## Build & CI

This project is intended to be validated primarily in GitHub Actions.

- Workflow: `.github/workflows/app-debug.yaml`
- CI tasks:
  - `:app:assembleDebug`
  - `:app:testDebugUnitTest`
- Artifacts:
  - Debug APK
  - Unit test reports

## Contributing

Contributions are welcome.

1. Fork the repository
2. Create a branch from `main`
3. Open a pull request with a clear change summary and test plan

## License

- Project license: [MIT](LICENSE)
- Dependency notices: [Third-Party Licenses](THIRD_PARTY_LICENSES.md)
