package com.example.life_cycleorganizer.ui.home

import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.life_cycleorganizer.R
import com.example.life_cycleorganizer.ui.components.MyBottomBar
import com.example.life_cycleorganizer.ui.theme.*
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.life_cycleorganizer.ui.focus.FocusViewModel
import com.example.life_cycleorganizer.ui.settings.SettingsViewModel
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext
import com.example.life_cycleorganizer.ui.utils.AlarmHelper

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FocusScreen(
    navController: NavController,
    focusViewModel: FocusViewModel,
    settingsViewModel: SettingsViewModel
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(
        settingsViewModel.selectedFocusDefault,
        settingsViewModel.selectedRestDefault,
        focusViewModel.selectedMode
    ) {

        val defaultMinutes =
            if (focusViewModel.selectedMode == "Focus") {
                settingsViewModel.getFocusMinutes()
            } else {
                settingsViewModel.getRestMinutes()
            }

        focusViewModel.applyDefaultTime(defaultMinutes)
    }

    LaunchedEffect(focusViewModel.isTimerRunning) {

        if (focusViewModel.isTimerRunning) {

            while (
                focusViewModel.remainingTimeInMillis > 0 &&
                focusViewModel.isTimerRunning
            ) {
                delay(1000L)
                focusViewModel.tick()
            }

            if (focusViewModel.remainingTimeInMillis <= 0) {

                focusViewModel.isTimerRunning = false

                if (settingsViewModel.isAlarmEnabled) {

                    val alarmUri =
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

                    focusViewModel.ringtone =
                        RingtoneManager.getRingtone(context, alarmUri)

                    focusViewModel.ringtone?.play()
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            MyBottomBar(
                navController = navController,
                selectedScreen = "Focus"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(neutral_1)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .background(
                        color = primary_5,),
            ) {
                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = "Focus",
                    fontFamily = OpenSansFamily,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = neutral_1,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "One Step At A Time",
                    fontFamily = LatoFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.25.em,
                    color = neutral_1,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                FocusTimePickerCard(
                    hour = focusViewModel.inputHour,
                    minute = focusViewModel.inputMinute,
                    onHourChange = { focusViewModel.onHourChange(it) },
                    onMinuteChange = { focusViewModel.onMinuteChange(it) },
                    onOkClick = { focusViewModel.formatInput() },
                    onCancelClick = { focusViewModel.resetToDefault() },
                    focusViewModel = focusViewModel,       // Thêm tham số
                    settingsViewModel = settingsViewModel
                )

                Spacer(modifier = Modifier.height(30.dp))

                FocusTimerCircle(
                    progress = focusViewModel.currentProgress,
                    timeText = focusViewModel.timerDisplayTime
                )

                Spacer(modifier = Modifier.height(30.dp))

                FocusControls(
                    selectedButton = focusViewModel.selectedButton,
                    onPlay = { focusViewModel.startTimer() },
                    onStop = { focusViewModel.stopTimer() },
                    onRestart = { focusViewModel.restartTimer() }
                )
            }
        }
    }
}

@Composable
fun FocusModeToggle(
    focusViewModel: FocusViewModel,
    settingsViewModel: SettingsViewModel
) {
    val selectedMode = focusViewModel.selectedMode

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(primary_3.copy(alpha = 0.3f), CircleShape)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("Focus", "Rest").forEach { mode ->
            val isSelected = (mode == selectedMode)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = if (isSelected) primary_4 else primary_3,
                        shape = CircleShape
                    )
                    .clickable {
                        focusViewModel.selectedMode = mode

                        val defaultMinutes = if (mode == "Focus") {
                            settingsViewModel.getFocusMinutes()
                        } else {
                            settingsViewModel.getRestMinutes()
                        }
                        focusViewModel.applyDefaultTime(defaultMinutes)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = mode,
                    color = neutral_1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun TimeInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(width = 80.dp, height = 50.dp),
            shape = RoundedCornerShape(10.dp),
            color = neutral_1
        ) {
            Box(contentAlignment = Alignment.Center) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Normal,
                        color = primary_5,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = primary_5,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun FocusTimePickerCard(
    hour: String,
    minute: String,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    onOkClick: () -> Unit,
    onCancelClick: () -> Unit,
    focusViewModel: FocusViewModel,
    settingsViewModel: SettingsViewModel
) {

    val focusManager = LocalFocusManager.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = primary_1) 
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FocusModeToggle(focusViewModel, settingsViewModel)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ENTER TIME",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = primary_5
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TimeInputField(
                    value = hour,
                    onValueChange = onHourChange,
                    label = "Hour"
                )

                Text(
                    text = ":",
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 20.dp),
                    fontSize = 36.sp,
                    color = primary_5
                )

                TimeInputField(
                    value = minute,
                    onValueChange = onMinuteChange,
                    label = "Minute"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_focus_card),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Cancel",
                    color = primary_5,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clickable { onCancelClick() }
                )
                Text(
                    text = "Ok",
                    color = primary_5,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clickable {
                            focusManager.clearFocus()
                            onOkClick()
                        }
                )
            }
        }
    }
}

@Composable
fun FocusTimerCircle(
    progress: Float,
    timeText: String
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(240.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()

            drawCircle(
                color = neutral_2.copy(alpha = 0.2f),
                style = Stroke(width = strokeWidth)
            )

            drawArc(
                color = primary_5,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = timeText,
                fontSize = 36.sp,
                fontWeight = FontWeight.Normal,
                color = primary_5
            )
            Text(
                text = "Remaining time",
                fontSize = 14.sp,
                color = primary_5
            )
        }
    }
}

@Composable
fun FocusControls(
    selectedButton: Int,
    onPlay: () -> Unit,
    onStop: () -> Unit,
    onRestart: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ControlButtonClick(
            iconRes = R.drawable.ic_play,
            onClick = onPlay,
            backgroundColor = if (selectedButton == 1) primary_3.copy(alpha = 0.5f) else primary_3.copy(alpha = 0.1f)
        )

        Spacer(modifier = Modifier.width(24.dp))

        ControlButtonClick(
            iconRes = R.drawable.ic_stop,
            onClick = onStop,
            backgroundColor = if (selectedButton == 2) primary_3.copy(alpha = 0.5f) else primary_3.copy(alpha = 0.1f)
        )

        Spacer(modifier = Modifier.width(24.dp))

        ControlButtonClick(
            iconRes = R.drawable.ic_restart,
            onClick = onRestart,
            backgroundColor = if (selectedButton == 3) primary_3.copy(alpha = 0.5f) else primary_3.copy(alpha = 0.1f)
        )
    }
}

@Composable
fun ControlButtonClick(
    iconRes: Int,
    onClick: () -> Unit,
    backgroundColor: Color
) {
    Surface(
        modifier = Modifier
            .size(60.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = primary_5
            )
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    widthDp = 360,
    heightDp = 1000,
    showBackground = true
)
@Composable
fun PreviewFocusScreen() {

    val mockNavController = rememberNavController()
    val mockFocusViewModel = FocusViewModel()
    val mockSettingsViewModel = SettingsViewModel()

    LifeCycleOrganizerTheme {
        FocusScreen(
            navController = mockNavController,
            focusViewModel = mockFocusViewModel,
            settingsViewModel = mockSettingsViewModel
        )
    }
}