package com.easylife.diary.feature.diary

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easylife.diary.core.designsystem.motion.adjustedDuration
import com.easylife.diary.core.designsystem.motion.rememberReduceMotionEnabled
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.core.designsystem.components.entry.EmptyEntryList
import com.easylife.diary.core.designsystem.components.entry.EntryList
import com.easylife.diary.core.navigation.screen.DiaryArgs
import com.easylife.diary.feature.diary.components.DiaryScreenTopBar
import androidx.compose.animation.core.tween
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by erenalpaslan on 1.01.2023
 */
class DiaryScreen : BaseScreen<DiaryViewModel>() {

    @Composable
    override fun Screen() {
        val uiState by viewModel.uiSate.collectAsStateWithLifecycle()

        LaunchedEffect(viewModel) {
            navigator.resultFlow<Boolean>(DiaryArgs.ENTRY_AFFECTED).onEach {
                if (it) {
                    viewModel.getAllEntries()
                }
            }.launchIn(this)
        }

        Content(uiState = uiState)
    }

    @Composable
    fun Content(uiState: DiaryUiState) {
        val reduceMotion = rememberReduceMotionEnabled()
        Scaffold(
            topBar = {
                DiaryScreenTopBar(
                    isEmpty = uiState is DiaryUiState.EmptyDiary,
                    streak = if (uiState is DiaryUiState.DataLoaded) uiState.streak else 0,
                    pts = if (uiState is DiaryUiState.DataLoaded) uiState.pts else 0,
                    onSearched = {text ->
                        viewModel.onSearch(text)
                    },
                    onCleared = {
                        viewModel.onSearch(null)
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                AnimatedContent(
                    targetState = uiState,
                    transitionSpec = {
                        fadeIn(tween(adjustedDuration(380, reduceMotion))) togetherWith
                            fadeOut(tween(adjustedDuration(220, reduceMotion)))
                    },
                    label = "diaryState"
                ) { currentState ->
                    when (currentState) {
                        DiaryUiState.Loading -> {}
                        DiaryUiState.EmptyDiary -> {
                            EmptyEntryList(
                                message = "Write down your thoughts and track your mood to get insights. Add your first entry"
                            )
                        }
                        is DiaryUiState.DataLoaded -> {
                            EntryList(list = currentState.data, onItemClicked = {entry ->
                                viewModel.navigateWithEntry(entry)
                            })
                        }
                    }
                }
            }
        }
    }


}