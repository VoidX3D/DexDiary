package com.easylife.diary.feature.insight

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.feature.insight.components.DiaryAnalyticsView
import com.easylife.diary.feature.insight.components.DiaryStreakView
import com.easylife.diary.feature.insight.components.MoodGraph
import com.easylife.diary.feature.insight.components.TrendsView
import com.easylife.diary.feature.insight.components.ai.AIOracleSummaryCard
import com.easylife.diary.feature.insight.components.ai.AIOracleVibeCard

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
                DiaryAnalyticsView()
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
            }
        }
    }
}