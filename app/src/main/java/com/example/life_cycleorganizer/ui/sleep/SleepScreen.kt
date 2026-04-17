package com.example.life_cycleorganizer.ui.sleep

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.life_cycleorganizer.R
import com.example.life_cycleorganizer.ui.components.MyBottomBar
import com.example.life_cycleorganizer.ui.home.TimeInputField
import com.example.life_cycleorganizer.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SleepScreen(
    navController: NavController,
    viewModel: SleepViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            MyBottomBar(
                navController = navController,
                selectedScreen = "Sleep"
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
                    .background(color = primary_5),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = "Sleep",
                    fontFamily = OpenSansFamily,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = neutral_1
                )
                Text(
                    text = "15-minutes fall asleep + cycle (90 minutes)",
                    fontFamily = LatoFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    color = neutral_1
                )
            }

            StartSleepCard(viewModel = viewModel)


            Text(
                text = "SHOULD WAKE UP AT",
                fontFamily = OpenSansFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = primary_5,
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 8.dp)
            )

            SleepCycleList(results = viewModel.sleepResults)

            SleepTipNote()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StartSleepCard(viewModel: SleepViewModel) {
    val focusManager = androidx.compose.ui.platform.LocalFocusManager.current

    Surface(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        color = primary_1
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("START SLEEPING AT", color = primary_5, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                Icon(painterResource(R.drawable.ic_sleep_card), null, tint = primary_5)
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TimeInputField(viewModel.inputHour, { viewModel.onHourChange(it) }, "Hour")
                Text(" : ", fontSize = 36.sp, color = primary_5)
                TimeInputField(viewModel.inputMinute, { viewModel.onMinuteChange(it) }, "Minute")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding (horizontal = 12.dp)
            ) {

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Ok",
                    modifier = Modifier
                        .clickable {
                            viewModel.calculateSleepCycles()
                            focusManager.clearFocus()
                        },
                    color = primary_5, fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Composable
fun SleepCycleList(results: List<String>) {
    val labels = listOf("1 cycle - 1h30m", "2 cycle - 3h", "3 cycle - 4h30m", "4 cycle - 6h", "5 cycle - 7h30m", "6 cycle - 9h")
    val statusTexts = listOf("Too Little", "Sleep Deprived", "Light Sleep", "Moderate Sleep", "Sufficient Sleep", "Optimal Sleep")
    val statusColors = listOf(status_3, status_3, status_4, status_4, status_5, status_5)

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        for (i in 0 until 3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(2) { j ->
                    val index = i * 2 + j
                    if (index < results.size) {
                        SleepItem(
                            time = results[index],
                            label = labels[index],
                            status = statusTexts[index],
                            statusColor = statusColors[index],
                            isSufficient = index == 4,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun SleepItem(
    time: String,
    label: String,
    status: String,
    statusColor: Color,
    isSufficient: Boolean,
    modifier: Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = neutral_2,
        border = if (isSufficient) BorderStroke(2.dp, status_5) else null
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = time, fontSize = 24.sp, color = primary_5, fontWeight = FontWeight.Bold)
            Text(text = label, color = neutral_4, fontSize = 12.sp)
            Text(text = status, color = statusColor, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SleepTipNote() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = 4.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(12.dp)
                )
                .blur(8.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = neutral_2
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_bulb),
                    contentDescription = null,
                    tint = status_6,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "You should wake up at the end of the sleep cycle to avoid feeling tired. Avoid waking up in the middle of a deep sleep cycle.",
                    fontSize = 14.sp,
                    color = primary_5,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    widthDp = 360,
    heightDp = 800,
    showBackground = true
)
@Composable
fun PreviewSleepScreen() {
    val mockNavController = rememberNavController()

    LifeCycleOrganizerTheme {
        SleepScreen(
            navController = mockNavController,
        )
    }
}