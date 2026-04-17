package com.example.life_cycleorganizer.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    var focusDefaultOptions = listOf("30 minutes", "1 hour")
    var selectedFocusDefault by mutableStateOf(focusDefaultOptions[0])

    var restDefaultOptions = listOf("5 minutes", "10 minutes")
    var selectedRestDefault by mutableStateOf(restDefaultOptions[0])

    var isAlarmEnabled by mutableStateOf(true)

    fun getFocusMinutes(): String = if (selectedFocusDefault == "1 hour") "60" else "30"
    fun getRestMinutes(): String = if (selectedRestDefault == "10 minutes") "10" else "05"
}