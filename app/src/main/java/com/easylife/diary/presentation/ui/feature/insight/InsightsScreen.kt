package com.easylife.diary.feature.insight

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easylife.diary.core.designsystem.motion.adjustedDuration
import com.easylife.diary.core.designsystem.motion.rememberReduceMotionEnabled
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.feature.insight.components.DiaryAnalyticsView
import com.easylife.diary.feature.insight.components.DiaryStreakView
import com.easylife.diary.feature.insight.components.MoodGraph
import com.easylife.diary.feature.insight.components.TrendsView
import com.easylife.diary.feature.insight.components.ai.AIOracleSummaryCard
import com.easylife.diary.feature.insight.components.ai.AIOracleVibeCard
import androidx.compose.animation.core.tween

/**
 * Created by erenalpaslan on 1.01.2023
 */
class InsightsScreen : BaseScreen<InsightsViewModel>() {
    @Composable
    override fun Screen() {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        Content(uiState)
    }

    @Composable
    fun Content(
        uiState: InsightsUiState
    ) {
        val scrollableState = rememberScrollState()
        val reduceMotion = rememberReduceMotionEnabled()

        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text("Insights")
                })
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .verticalScroll(scrollableState)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(adjustedDuration(350, reduceMotion))) +
                        slideInVertically(
                            initialOffsetY = { if (reduceMotion) 0 else it / 4 },
                            animationSpec = tween(adjustedDuration(350, reduceMotion))
                        )
                ) { DiaryAnalyticsView() }
                Spacer(modifier = Modifier.height(16.dp))
                DiaryStreakView(
                    longestChain = uiState.longestChain,
                    streakData = uiState.streakData
                )
                Spacer(modifier = Modifier.height(32.dp))
                
                // AI Oracle Features
                AIOracleSummaryCard(summary = uiState.aiWeeklySummary ?: "AI Summary loading...")
                Spacer(modifier = Modifier.height(16.dp))
                AIOracleVibeCard(vibe = uiState.aiVibe ?: "AI Vibe loading...")
                Spacer(modifier = Modifier.height(16.dp))
                AchievementCard(achievements = uiState.achievements)
            }
        }
    }
}

@Composable
private fun AchievementCard(achievements: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Achievements", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (achievements.isEmpty()) {
                Text("No achievements unlocked yet.", style = MaterialTheme.typography.bodyMedium)
            } else {
                Text(achievements.joinToString(separator = "\n") { "• $it" }, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}