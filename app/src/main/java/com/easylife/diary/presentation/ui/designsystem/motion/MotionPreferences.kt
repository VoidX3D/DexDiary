package com.easylife.diary.core.designsystem.motion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object MotionPreferences {
    private val _reduceMotion = MutableStateFlow(false)
    val reduceMotion: StateFlow<Boolean> = _reduceMotion.asStateFlow()

    fun setReduceMotion(enabled: Boolean) {
        _reduceMotion.value = enabled
    }
}

@Composable
fun rememberReduceMotionEnabled(): Boolean {
    val reduce by MotionPreferences.reduceMotion.collectAsState()
    return reduce
}

fun adjustedDuration(baseDuration: Int, reduceMotion: Boolean): Int {
    if (!reduceMotion) return baseDuration
    return (baseDuration * 0.35f).toInt().coerceAtLeast(80)
}
