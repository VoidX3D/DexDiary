# 🤖 Dex Diary: System Logic & Mechanics

This document defines the core engine for Dex Diary. Use this as the "Source of Truth" for rebuilding the application's behavior.

---

## 0. Build + Environment Rules
*   **OS Baseline**: Assume Ubuntu/Linux environment.
*   **CI-First Validation**: Prefer GitHub Actions as the canonical build/test execution path.
*   **No Windows Assumptions**: Avoid Windows-only scripts and instructions.
*   **Open Source Requirement**: Keep project documentation/license files accurate and present (`README.md`, `LICENSE`, and third-party notices).

## 1. The Diary Entry Logic (The Golden Rule)
*   **Temporal Constraint**: Users can ONLY write or edit the entry for the current calendar day (`YYYY-MM-DD`). 
*   **Zero-Edit History**: Once a day passes, the entry for that day is locked. This creates "High Stakes" journaling—if you don't capture the thought today, it is lost to time.

## 2. The Streak & Coin System
*   **The Streak**: Increments by 1 for every consecutive day an entry is saved. 
*   **The Multiplier**: 
    *   1-2 Days: 1.0x
    *   3-6 Days: 1.5x
    *   7-13 Days: 2.0x
    *   14-29 Days: 2.5x
    *   30+ Days: 3.0x
*   **Coin Generation (PTS)**: 
    *   `Base`: 10 PTS per entry.
    *   `Word Bonus`: +5 PTS per 250 words.
    *   `Media Bonus`: +5 PTS if an image or audio is attached.
    *   `Formula`: `(Base + Bonuses) * Multiplier`.

## 3. The Shop & Inventory Logic
*   **Currency**: "PTS" (earned through writing).
*   **Consumables**:
    *   `Streak Freeze`: Prevents a streak from resetting if a day is missed. Must be purchased *before* the miss. Max 2 in inventory.
    *   `Double Points`: A 24-hour power-up that doubles all earned PTS.
*   **Permanent Unlocks (Themes)**:
    *   **The Shadow (Dark Mode)**: Costs 5,000 PTS. This is the ultimate "End Game" unlock.
    *   **Premium Palettes**: Sepia, Ocean, Forest, etc. (Variable costs).

## 4. Achievement Engine
*   **Milestones**:
    *   `The Novice`: Write 1,000 total words.
    *   `The Monk`: Maintain a 30-day streak.
    *   `The Alchemist`: Unlock 3 different themes.
    *   `Night Owl`: Write an entry between 12 AM and 4 AM.
*   Achievements provide permanent profile badges and large one-time PTS rewards.

## 5. Notification Logic (The Nudge)
*   **Standard**: A gentle reminder at 8 PM if today's entry is empty.
*   **Urgent**: At 11 PM, a "Streak at Risk!" warning.
*   **The Oracle**: Morning notification predicting the day's "Vibe" based on previous weeks' moods.
