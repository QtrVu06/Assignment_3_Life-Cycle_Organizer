package com.example.life_cycleorganizer.ui.focus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.media.Ringtone

class FocusViewModel : ViewModel() {
    var inputHour by mutableStateOf("02")
    var inputMinute by mutableStateOf("00")
    var selectedMode by mutableStateOf("Focus")

    var timerDisplayTime by mutableStateOf("02 : 00")
    var currentProgress by mutableStateOf(1.0f)
    var isTimerRunning by mutableStateOf(false)
    var selectedButton by mutableStateOf(0)

    var isInitialized by mutableStateOf(false)
    var ringtone: Ringtone? = null

    var totalTimeInMillis by mutableStateOf(7200000L)
    var remainingTimeInMillis by mutableStateOf(7200000L)

    fun onHourChange(newValue: String) {
        if (newValue.length <= 2 && newValue.all { it.isDigit() }) {
            inputHour = newValue
        }
    }

    fun onMinuteChange(newValue: String) {
        if (newValue.length <= 2 && newValue.all { it.isDigit() }) {
            inputMinute = newValue
        }
    }

    fun formatInput() {
        inputHour = inputHour.padStart(2, '0')
        inputMinute = inputMinute.padStart(2, '0')

        timerDisplayTime = "$inputHour : $inputMinute"

        val h = inputHour.toLongOrNull() ?: 0L
        val m = inputMinute.toLongOrNull() ?: 0L

        totalTimeInMillis = (h * 3600 + m * 60) * 1000
        remainingTimeInMillis = totalTimeInMillis

        currentProgress = 1.0f
        isTimerRunning = false
    }

    fun resetToDefault() {
        inputHour = "00"
        inputMinute = "00"
        timerDisplayTime = "00 : 00"
        currentProgress = 1f
        totalTimeInMillis = 0L
        remainingTimeInMillis = 0L
        isTimerRunning = false
        selectedButton = 0
    }

    fun startTimer() {
        if (remainingTimeInMillis > 0) {
            isTimerRunning = true
            selectedButton = 1
        }
    }

    fun stopTimer() {

        ringtone?.stop()

        isTimerRunning = false
        selectedButton = 2
    }

    fun restartTimer() {

        ringtone?.stop()

        isTimerRunning = false
        remainingTimeInMillis = totalTimeInMillis
        currentProgress = 1.0f

        val totalSeconds = remainingTimeInMillis / 1000
        val h = totalSeconds / 3600
        val m = (totalSeconds % 3600) / 60
        timerDisplayTime = "${h.toString().padStart(2, '0')} : ${m.toString().padStart(2, '0')}"

        selectedButton = 3
    }

    fun tick() {
        if (remainingTimeInMillis > 0) {
            remainingTimeInMillis -= 1000L

            currentProgress = remainingTimeInMillis.toFloat() / totalTimeInMillis

            val totalSeconds = remainingTimeInMillis / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60

            timerDisplayTime = if (hours > 0) {
                "${hours.toString().padStart(2, '0')} : ${minutes.toString().padStart(2, '0')}"
            } else {
                "${minutes.toString().padStart(2, '0')} : ${seconds.toString().padStart(2, '0')}"
            }
        } else {
            isTimerRunning = false
        }
    }

    fun applyDefaultTime(minutes: String) {

        isTimerRunning = false

        val totalMinutes = minutes.toIntOrNull() ?: 0

        val hours = totalMinutes / 60
        val mins = totalMinutes % 60

        inputHour = hours.toString().padStart(2, '0')
        inputMinute = mins.toString().padStart(2, '0')

        formatInput()
        timerDisplayTime = "$inputHour : $inputMinute"
        selectedButton = 0
    }
}