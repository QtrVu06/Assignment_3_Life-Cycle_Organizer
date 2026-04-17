package com.example.life_cycleorganizer.ui.utils

fun convertToDays(value: Int, unit: String): Int {
    return when (unit) {
        "Day(s)" -> value
        "Week(s)" -> value * 7
        "Month(s)" -> value * 30
        else -> value
    }
}

fun formatDuration(days: Int): String {

    var remainingDays = days

    val years = remainingDays / 365
    remainingDays %= 365

    val months = remainingDays / 30
    remainingDays %= 30

    val weeks = remainingDays / 7
    remainingDays %= 7

    val d = remainingDays

    val parts = mutableListOf<String>()

    if (years > 0) parts.add("${years}y")
    if (months > 0) parts.add("${months}mo")
    if (weeks > 0) parts.add("${weeks}w")
    if (d > 0) parts.add("${d}d")

    return if (parts.isEmpty()) "1d" else parts.joinToString(" ")
}