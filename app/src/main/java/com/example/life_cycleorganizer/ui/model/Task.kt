package com.example.life_cycleorganizer.ui.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.life_cycleorganizer.ui.home.TimeTab
import java.time.LocalDate

data class Task(
    val icon: String,
    val title: String,
    val goal: String,
    val loop: TimeTab,
    val streak: Int,
    val progress: Float,
    val isFinished: Boolean,

    val repeatDays: Int,
    val loopDisplay: String,

    val lastUpdatedDate: LocalDate = LocalDate.now()
)