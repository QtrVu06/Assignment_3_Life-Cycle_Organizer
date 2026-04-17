package com.example.life_cycleorganizer.ui.home

import com.example.life_cycleorganizer.R
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.example.life_cycleorganizer.ui.components.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.i18n.DateTimeFormatter
import com.example.life_cycleorganizer.ui.theme.Lato
import com.example.life_cycleorganizer.ui.theme.LatoFamily
import com.example.life_cycleorganizer.ui.theme.OpenSans
import com.example.life_cycleorganizer.ui.theme.OpenSansFamily
import com.example.life_cycleorganizer.ui.theme.neutral_1
import com.example.life_cycleorganizer.ui.theme.primary_1
import com.example.life_cycleorganizer.ui.theme.primary_5
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.life_cycleorganizer.ui.model.Task
import com.example.life_cycleorganizer.ui.theme.neutral_2
import com.example.life_cycleorganizer.ui.theme.neutral_3
import com.example.life_cycleorganizer.ui.theme.neutral_4
import com.example.life_cycleorganizer.ui.theme.primary_2
import com.example.life_cycleorganizer.ui.theme.primary_3
import com.example.life_cycleorganizer.ui.utils.formatDate
import com.example.life_cycleorganizer.ui.utils.getGreeting
import com.google.android.libraries.places.api.model.LocalDate
import java.time.LocalDateTime
import com.example.life_cycleorganizer.ui.utils.convertToBestLoop
import com.example.life_cycleorganizer.ui.utils.convertToDays
import com.example.life_cycleorganizer.ui.utils.formatDuration
import com.example.life_cycleorganizer.ui.utils.convertDaysToTimeTab

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewHabitScreen(
    onBack: () -> Unit,
    homeViewModel: HomeViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    var taskTitle by remember { mutableStateOf("") }
    var taskIcon by remember { mutableStateOf("🎯") }

    var isDaily by remember { mutableStateOf(true) }
    var customValue by remember { mutableStateOf("1") }
    var customUnit by remember { mutableStateOf("Day(s)") }

    var isTimeGoal by remember { mutableStateOf(true) }
    var timeValue by remember { mutableStateOf("0") }
    var timeUnit by remember { mutableStateOf("Minute(s)") }
    var customGoal by remember { mutableStateOf("") }

    val isFormValid = taskTitle.isNotBlank() && taskTitle.length <= 20 && (if (isTimeGoal) timeValue.toIntOrNull() ?: 0 > 0 else customGoal.isNotBlank())

    val resetData = {
        taskTitle = ""
        taskIcon = "🎯"
        isDaily = true
        customValue = "1"
        customUnit = "Day(s)"
        isTimeGoal = true
        timeValue = "0"
        timeUnit = "Minute(s)"
        customGoal = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(neutral_1)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
                .background(color = primary_5),
        ) {

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "New Habit",
                fontFamily = OpenSansFamily,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = neutral_1,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NameEmoji_Habit(
                taskIcon = taskIcon,
                taskTitle = taskTitle,
                onTitleChange = { if (it.length <= 20) taskTitle = it },
                onIconChange = { taskIcon = it }
            )

            StartFrom_Habit()

            Loop_Habit(
                isDaily = isDaily,
                onModeChange = { isDaily = it },
                customValue = customValue,
                onValueChange = { customValue = it },
                customUnit = customUnit,
                onUnitChange = { customUnit = it }
            )

            Goal_Habit(
                isTimeGoal = isTimeGoal,
                onModeChange = { isTimeGoal = it },
                timeValue = timeValue,
                onTimeValueChange = { timeValue = it },
                timeUnit = timeUnit,
                onTimeUnitChange = { timeUnit = it },
                customGoal = customGoal,
                onCustomGoalChange = { customGoal = it }
            )

            NavBar_Habit(
                onBack = onBack,
                onReset = resetData,
                isCreateEnabled = isFormValid,
                onCreate = {

                    if (isFormValid) {

                        val repeatDays = if (isDaily) {
                            1
                        } else {
                            convertToDays(
                                customValue.toIntOrNull() ?: 1,
                                customUnit
                            )
                        }

                        val loopDisplay = formatDuration(repeatDays)
                        val loop = convertDaysToTimeTab(repeatDays)

                        val newTask = Task(
                            icon = taskIcon,
                            title = taskTitle,
                            goal = formatGoalDisplay(
                                isTimeGoal,
                                timeValue,
                                timeUnit,
                                customGoal
                            ),
                            loop = loop,
                            streak = 0,
                            progress = 0f,
                            isFinished = false,
                            repeatDays = repeatDays,
                            loopDisplay = loopDisplay
                        )

                        homeViewModel.addTask(newTask)

                        onBack()
                    }
                }
            )
        }
    }
}

@Composable
fun NameEmoji_Habit(
    taskIcon: String,
    taskTitle: String,
    onTitleChange: (String) -> Unit,
    onIconChange: (String) -> Unit
) {

    var emojiInput by remember { mutableStateOf("") }

    Text(
        text = "NAME & EMOJI",
        fontWeight = FontWeight.Bold,
        fontFamily = LatoFamily,
        fontSize = 14.sp,
        color = primary_5,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(
            modifier = Modifier
                .size(56.dp)
                .background(primary_2, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {

            BasicTextField(
                value = emojiInput,
                onValueChange = { newValue ->

                    emojiInput = newValue

                    if (newValue.isNotEmpty()) {
                        onIconChange(newValue)
                        emojiInput = ""
                    }
                },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                ),
                decorationBox = {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = taskIcon,
                            fontSize = 24.sp
                        )
                    }
                }
            )
        }

        OutlinedTextField(
            value = taskTitle,
            textStyle = TextStyle(
                color = primary_5,
                fontSize = 20.sp,
                fontFamily = LatoFamily,
                fontWeight = FontWeight.Bold
            ),
            onValueChange = onTitleChange,
            placeholder = { Text("Enter habit name...", color = neutral_4) },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primary_5,
                unfocusedBorderColor = primary_2
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartFrom_Habit() {
    val displayDate = formatDate(LocalDateTime.now())

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "START FROM",
            fontFamily = LatoFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = primary_5
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(primary_1, RoundedCornerShape(16.dp))
                .border(1.dp, primary_2, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = primary_5,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = displayDate,
                    fontFamily = LatoFamily,
                    fontSize = 16.sp,
                    color = primary_5,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun Loop_Habit(
    isDaily: Boolean,
    onModeChange: (Boolean) -> Unit,
    customValue: String,
    onValueChange: (String) -> Unit,
    customUnit: String,
    onUnitChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val units = listOf("Day(s)", "Week(s)", "Month(s)")

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("LOOP", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = primary_5, fontFamily = LatoFamily)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(primary_2, RoundedCornerShape(24.dp))
                .padding(4.dp)
        ) {
            listOf(true, false).forEach { mode ->
                val selected = isDaily == mode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (selected) primary_5 else Color.Transparent)
                        .clickable { onModeChange(mode) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (mode) "Daily" else "Custom",
                        color = if (selected) Color.White else primary_5,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (isDaily) 0.5f else 1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = customValue,
                onValueChange = { if (!isDaily) onValueChange(it) },
                modifier = Modifier.weight(1f),
                enabled = !isDaily,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primary_5,
                    unfocusedBorderColor = primary_2
                )
            )

            Box(modifier = Modifier.weight(1f)) {
                OutlinedCard(
                    onClick = { if (!isDaily) expanded = true },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, primary_2)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(customUnit, color = primary_5)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_down),
                            contentDescription = "Drop-Down List",
                            modifier = Modifier.size(12.dp),
                            tint = neutral_3
                        )
                    }
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    units.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                onUnitChange(unit)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Goal_Habit(
    isTimeGoal: Boolean,
    onModeChange: (Boolean) -> Unit,
    timeValue: String,
    onTimeValueChange: (String) -> Unit,
    timeUnit: String,
    onTimeUnitChange: (String) -> Unit,
    customGoal: String,
    onCustomGoalChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val units = listOf("Minute(s)", "Hour(s)")

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("GOAL", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = primary_5, fontFamily = LatoFamily)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(neutral_2, RoundedCornerShape(16.dp))
                .border(1.dp, primary_2, RoundedCornerShape(24.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onModeChange(true) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .border(2.dp, primary_5, CircleShape)
                            .padding(4.dp)
                    ) {
                        if (isTimeGoal) {
                            Box(modifier = Modifier.fillMaxSize().background(primary_5, CircleShape))
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Time", fontSize = 16.sp, color = primary_5, fontWeight = FontWeight.SemiBold)
                }

                Row(
                    modifier = Modifier.fillMaxWidth().alpha(if (isTimeGoal) 1f else 0.5f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = timeValue,
                        onValueChange = { if (isTimeGoal) onTimeValueChange(it) },
                        modifier = Modifier.weight(1f),
                        enabled = isTimeGoal,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = primary_2)
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedCard(
                            onClick = { if (isTimeGoal) expanded = true },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            border = BorderStroke(1.dp, primary_2)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(timeUnit, color = primary_5)
                                Icon(painterResource(id = R.drawable.ic_down), null, tint = primary_5, modifier = Modifier.size(16.dp))
                            }
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            units.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text(unit) },
                                    onClick = { onTimeUnitChange(unit); expanded = false }
                                )
                            }
                        }
                    }
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = primary_2
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onModeChange(false) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .border(2.dp, primary_5, CircleShape)
                            .padding(4.dp)
                    ) {
                        if (!isTimeGoal) {
                            Box(modifier = Modifier.fillMaxSize().background(primary_5, CircleShape))
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Custom", fontSize = 16.sp, color = primary_5, fontWeight = FontWeight.SemiBold)
                }

                OutlinedTextField(
                    value = customGoal,
                    onValueChange = { if (!isTimeGoal) onCustomGoalChange(it) },
                    modifier = Modifier.fillMaxWidth().alpha(if (!isTimeGoal) 1f else 0.5f),
                    enabled = !isTimeGoal,
                    placeholder = { Text("e.g. 2 liters", color = neutral_4) },
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    }
}

@Composable
fun NavBar_Habit(
    onBack: () -> Unit,
    onCreate: () -> Unit,
    onReset: () -> Unit,
    isCreateEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.height(44.dp).width(96.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primary_2), // Màu nền nhạt
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left),
                    contentDescription = "Back",
                    tint = neutral_4,
                    modifier = Modifier
                        .size(12.dp)
                )
                Text(
                    text = "Back",
                    color = neutral_4,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = LatoFamily
                )
            }
        }

        Button(
            onClick = onCreate,
            modifier = Modifier.height(44.dp).width(96.dp),
            enabled = isCreateEnabled,
            colors = ButtonDefaults.buttonColors(containerColor = if (isCreateEnabled) primary_2 else neutral_3),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Create", color = primary_5, fontWeight = FontWeight.Bold, fontSize = 12.sp, fontFamily = LatoFamily)
        }

        Button(
            onClick = onReset,
            modifier = Modifier.height(44.dp).width(96.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primary_2),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Reset", color = primary_5, fontWeight = FontWeight.Bold, fontSize = 12.sp, fontFamily = LatoFamily)
        }
    }
}

fun formatGoalDisplay(isTimeGoal: Boolean, value: String, unit: String, custom: String): String {
    if (!isTimeGoal) return custom

    val num = value.toIntOrNull() ?: 0
    return when (unit) {
        "Minute(s)" -> {
            val h = num / 60
            val m = num % 60
            if (h > 0) "${h}h ${m}m" else "${m}m"
        }
        "Hour(s)" -> "${num}h"
        else -> custom
    }
}

fun formatLoopAbbreviation(isDaily: Boolean, value: String, unit: String): String {
    if (isDaily) return "Daily"
    val unitAbbr = when (unit) {
        "Day(s)" -> "d"
        "Week(s)" -> "w"
        "Month(s)" -> "mo"
        else -> "d"
    }
    return "$value$unitAbbr"
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 1000)
@Composable
fun NewHabitScreenPreview() {
    NewHabitScreen(onBack = {})
}