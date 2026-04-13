package com.easylife.diary.delete.data

import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.data.repository.EntryRepository
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.core.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by erenalpaslan on 6.01.2023
 */
@HiltViewModel
class DeleteDataViewModel @Inject constructor(
    private val entryRepository: EntryRepository,
    private val preferencesManager: PreferencesManager,
    private val navigator: DiaryNavigator
): BaseViewModel() {
    fun deleteAllData() {
        viewModelScope.launch {
            entryRepository.deleteAllEntries()
            preferencesManager.clearAll()
            navigator.navigate(DiaryRoutes.splashRoute) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}