package com.example.life_cycleorganizer.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getGreeting(time: LocalDateTime): String {
    val hour = time.hour

    return when (hour) {
        in 5..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(time: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
    return time.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTodayDate(): LocalDate {
    return LocalDate.now()
}

@RequiresApi(Build.VERSION_CODES.O)
fun isNewDay(lastDate: LocalDate): Boolean {
    return lastDate.isBefore(LocalDate.now())
}