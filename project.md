# DexDiary Project Definition

DexDiary is a free, open-source Android journaling app built around consistency, reflection, and game-style progression.

## Platform & Build Constraints

- Primary dev environment: Ubuntu (Linux)
- No Windows-specific build assumptions
- Validation source of truth: GitHub Actions CI only
- Required CI checks:
  - `:app:assembleDebug`
  - `:app:testDebugUnitTest`

## Product Direction

- Keep the app daily-use friendly and privacy-first
- Preserve the gamified loop (streaks, points, shop, achievements)
- Keep architecture unified under `app/src/main/java/com/easylife/diary/{di,data,domain,presentation}`
- Maintain accessible UI (including Reduce Motion behavior)
