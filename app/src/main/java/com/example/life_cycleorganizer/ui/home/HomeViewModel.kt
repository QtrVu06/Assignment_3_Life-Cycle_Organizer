package com.example.life_cycleorganizer.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.life_cycleorganizer.ui.model.Task
import com.example.life_cycleorganizer.ui.utils.getTodayDate
import com.example.life_cycleorganizer.ui.utils.isNewDay
import java.time.LocalDateTime
import java.time.LocalDate

enum class TimeTab { Daily, Weekly, Monthly, Yearly }
class HomeViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val currentTime = mutableStateOf(LocalDateTime.now())

    val tasks = mutableStateListOf<Task>(
        Task("📚","Study", "2h", TimeTab.Daily, 5, 0f, false, 1, "1d", LocalDate.now()),
        Task("✍️","Write Diary", "1h", TimeTab.Weekly, 3, 0f, false,7,"1w",LocalDate.now()),
        Task("🫗","Drink Water", "2 liters", TimeTab.Daily, 10, 1f, true,1,"1d",LocalDate.now()),
    )

    val unfinishedCount: Int
        get() = tasks.count { !it.isFinished }

    var selectedEmojiIndex = mutableStateOf(-1)

    fun selectEmoji(index: Int) {
        selectedEmojiIndex.value = index
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAndResetDailyStatus() {

        val today = getTodayDate()

        tasks.forEachIndexed { index, task ->

            if (isNewDay(task.lastUpdatedDate)) {

                var newStreak = task.streak

                if (task.isFinished && task.loop == TimeTab.Daily) {
                    newStreak += 1
                }

                if (!task.isFinished && task.loop == TimeTab.Daily) {
                    newStreak = 0
                }

                tasks[index] = task.copy(
                    streak = newStreak,
                    isFinished = false,
                    progress = 0f,
                    lastUpdatedDate = today
                )
            }
        }
    }

    var selectedTab = mutableStateOf(TimeTab.Daily)

    val filteredTasks: List<Task>
        get() = tasks.filter { it.loop == selectedTab.value }

    val finishedCount: Int get() = filteredTasks.count { it.isFinished }
    val totalCount: Int get() = filteredTasks.size
    val progress: Float get() = if (totalCount == 0) 0f else finishedCount.toFloat() / totalCount

    fun selectTab(tab: TimeTab) {
        selectedTab.value = tab
    }

    fun toggleTaskStatus(task: Task) {
        val index = tasks.indexOf(task)
        if (index != -1) {

            val currentTask = tasks[index]
            val newStatus = !currentTask.isFinished

            tasks[index] = currentTask.copy(
                isFinished = newStatus,
                progress = if (newStatus) 1f else 0f
            )
        }
    }

    fun addTask(newTask: Task) {
        tasks.add(newTask)
        println("DEBUG: tasks size = ${tasks.size}, new task = ${newTask.title}, loop = ${newTask.loop}")
    }


}