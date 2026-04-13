# 🤖 Dex Diary: System Logic & Mechanics

This document defines the core engine for Dex Diary. Use this as the "Source of Truth" for rebuilding the application's behavior.

---

## 1. The Diary Entry Logic (The Golden Rule)
*   **Temporal Constraint**: Users can ONLY write or edit the entry for the current calendar day (`YYYY-MM-DD`). 
*   **Zero-Edit History**: Once a day passes, the entry for that day is locked. This creates "High Stakes" journaling—if you don't capture the thought today, it is lost to time.
*   **Metadata Extraction**: Every entry must track:
    *   `Word Count`: Raw count of words.
    *   `Mood`: A single selectable emoji/ID (Happy, Sad, Angry, etc.).
    *   `Tags`: Comma-separated labels.
    *   `Complexity`: Calculation based on sentence structure and word variety.

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

## 4. Daily Missions
*   Every day, 3 random missions are generated:
    1.  **Quantity**: e.g., "Write 500 words today."
    2.  **Diversity**: e.g., "Use a mood other than Happy."
    3.  **Depth**: e.g., "Add 3 tags to your entry."
*   Completion rewards a flat bonus (10-50 PTS) and counts toward achievements.

## 5. Achievement Engine
*   **Milestones**:
    *   `The Novice`: Write 1,000 total words.
    *   `The Monk`: Maintain a 30-day streak.
    *   `The Alchemist`: Unlock 3 different themes.
    *   `Night Owl`: Write an entry between 12 AM and 4 AM.
*   Achievements provide permanent profile badges and large one-time PTS rewards.

## 6. Notification Logic (The Nudge)
*   **Standard**: A gentle reminder at 8 PM if today's entry is empty.
*   **Urgent**: At 11 PM, a "Streak at Risk!" warning.
*   **The Oracle**: Morning notification predicting the day's "Vibe" based on previous weeks' moods.

## 7. AI Oracle Engine
*   **Weekly Summary**: Aggregates the last 7 days of entries into a poetic, 3-sentence summary.
*   **Vibe Check**: Analyzes mood trends to tell the user their "Current Emotional Weather."
*   **Privacy**: All AI processing must be directed through local prompts or secure, transient API calls with no data retention.
