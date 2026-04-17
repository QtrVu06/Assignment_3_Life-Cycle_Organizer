package com.example.life_cycleorganizer.ui.sleep

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Calendar

class SleepViewModel : ViewModel() {
    // 1. Khởi tạo mặc định là 00:00
    var inputHour by mutableStateOf("00")
    var inputMinute by mutableStateOf("00")

    // Danh sách lưu 6 mốc thời gian tính toán được
    var sleepResults by mutableStateOf<List<String>>(emptyList())

    // 2. Tự động tính toán ngay khi ViewModel được khởi tạo để hiện bảng Should Wake Up At
    init {
        calculateSleepCycles()
    }

    fun onHourChange(newValue: String) {
        if (newValue.length <= 2 && newValue.all { it.isDigit() }) {
            val hourInt = newValue.toIntOrNull()
            // Chỉ cập nhật nếu là rỗng (đang xóa) hoặc nằm trong khoảng 0-23
            if (newValue.isEmpty() || (hourInt != null && hourInt <= 23)) {
                inputHour = newValue
            }
        }
    }

    // 2. Hàm giới hạn nhập Minute (Tối đa 2 số)
    fun onMinuteChange(newValue: String) {
        if (newValue.length <= 2 && newValue.all { it.isDigit() }) {
            val minuteInt = newValue.toIntOrNull()
            // Chỉ cập nhật nếu là rỗng hoặc nằm trong khoảng 0-59
            if (newValue.isEmpty() || (minuteInt != null && minuteInt <= 59)) {
                inputMinute = newValue
            }
        }
    }

    fun calculateSleepCycles() {

        inputHour = if (inputHour.isEmpty()) "00" else inputHour.padStart(2, '0')
        inputMinute = if (inputMinute.isEmpty()) "00" else inputMinute.padStart(2, '0')

        val h = inputHour.toIntOrNull() ?: 0
        val m = inputMinute.toIntOrNull() ?: 0

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, h)
        calendar.set(Calendar.MINUTE, m)


        calendar.add(Calendar.MINUTE, 15)
        val results = mutableListOf<String>()
        repeat(6) {
            calendar.add(Calendar.MINUTE, 90)
            val hour = calendar.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
            val minute = calendar.get(Calendar.MINUTE).toString().padStart(2, '0')
            results.add("$hour : $minute")
        }
        sleepResults = results
    }
}