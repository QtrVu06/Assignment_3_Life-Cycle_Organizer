package com.example.life_cycleorganizer.ui.utils

import com.example.life_cycleorganizer.ui.home.TimeTab

data class LoopResult(
    val tab: TimeTab,
    val display: String
)

fun convertToBestLoop(value: Int, unit: String): LoopResult {

    val totalDays = when (unit) {
        "Day(s)" -> value
        "Week(s)" -> value * 7
        "Month(s)" -> value * 30
        else -> value
    }

    return when {

        totalDays >= 30 -> {
            val month = totalDays / 30
            val remain = totalDays % 30
            LoopResult(
                TimeTab.Yearly,
                "${month}mo ${remain}d"
            )
        }

        totalDays >= 7 -> {
            val week = totalDays / 7
            val remain = totalDays % 7
            LoopResult(
                TimeTab.Monthly,
                "${week}w ${remain}d"
            )
        }

        totalDays <= 7 -> {
            val week = totalDays / 7
            val remain = totalDays % 7
            LoopResult(
                TimeTab.Weekly,
                "${week}w ${remain}d"
            )
        }

        else -> {
            LoopResult(
                TimeTab.Daily,
                "${totalDays}d"
            )
        }
    }
}

fun convertDaysToTimeTab(days: Int): TimeTab {
    return when {
        days >= 365 -> TimeTab.Yearly
        days >= 30 -> TimeTab.Monthly
        days >= 7 -> TimeTab.Weekly
        else -> TimeTab.Daily
    }
}