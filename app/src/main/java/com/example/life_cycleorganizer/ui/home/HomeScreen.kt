package com.example.life_cycleorganizer.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.example.life_cycleorganizer.ui.components.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.life_cycleorganizer.ui.theme.Lato
import com.example.life_cycleorganizer.ui.theme.LatoFamily
import com.example.life_cycleorganizer.ui.theme.OpenSans
import com.example.life_cycleorganizer.ui.theme.OpenSansFamily
import com.example.life_cycleorganizer.ui.theme.neutral_1
import com.example.life_cycleorganizer.ui.theme.primary_1
import com.example.life_cycleorganizer.ui.theme.primary_5
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.life_cycleorganizer.ui.model.Task
import com.example.life_cycleorganizer.ui.theme.LifeCycleOrganizerTheme
import com.example.life_cycleorganizer.ui.theme.primary_2
import com.example.life_cycleorganizer.ui.theme.primary_3
import com.example.life_cycleorganizer.ui.utils.formatDate
import com.example.life_cycleorganizer.ui.utils.getGreeting
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavHostController,
               homeViewModel: HomeViewModel) {

    val scrollState = rememberScrollState()

    val viewModel = homeViewModel
    val selectedTab by viewModel.selectedTab
    val currentTime = viewModel.currentTime.value
    val tasks = viewModel.tasks

    val finished = viewModel.finishedCount
    val total = viewModel.totalCount
    val progress = viewModel.progress

    LaunchedEffect(Unit) {
        viewModel.checkAndResetDailyStatus()
    }

    Scaffold(
        bottomBar = {
            MyBottomBar(
                navController = navController,
                selectedScreen = "Home"
            )
        },
        floatingActionButton = {
            AddTaskButton(onClick = {
                navController.navigate("new_habit_screen")
            })
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(neutral_1)
                .verticalScroll(scrollState)
        ) {

            TopSection(
                greeting = getGreeting(currentTime),
                date = formatDate(currentTime),
                finished = finished,
                total = total,
                progress = progress,
                viewModel = viewModel,
                selectedTab = selectedTab,
            )
            FilterSection(
                selectedTab = selectedTab,
                onTabClick = { viewModel.selectTab(it) }
            )
            TaskSection(
                tasks = viewModel.filteredTasks,
                onToggleStatus = { task -> viewModel.toggleTaskStatus(task) }
            )
            Spacer(modifier = Modifier.height(64.dp))

        }
    }
}

@Composable
fun TopSection(
    greeting: String,
    date: String,
    finished: Int,
    total: Int,
    progress: Float,
    viewModel: HomeViewModel,
    selectedTab: TimeTab
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(primary_5)
            .height(256.dp)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "$greeting!",
            fontFamily = OpenSansFamily,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = neutral_1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .wrapContentHeight(align = Alignment.CenterVertically, unbounded = true),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = date,
            fontFamily = LatoFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Italic,
            color = neutral_1,
            letterSpacing = 0.25.em,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .wrapContentHeight(align = Alignment.CenterVertically, unbounded = true),
        )

        Spacer(modifier = Modifier.height(20.dp))

        StatRow(
            finished = finished,
            total = total,
            progress = progress,
            viewModel = viewModel,
            selectedTab = selectedTab
        )
    }
}

@Composable
fun StatRow(
    finished: Int,
    total: Int,
    progress: Float,
    viewModel: HomeViewModel,
    selectedTab: TimeTab
) {

    val (title1, title3) = when (selectedTab) {
        TimeTab.Daily -> "Daily Completed" to "Today"
        TimeTab.Weekly -> "Weekly Completed" to "This Week"
        TimeTab.Monthly -> "Monthly Completed" to "This Month"
        TimeTab.Yearly -> "Yearly Completed" to "This Year"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatCard1_3(
            current = finished.toString(),
            total = " / $total",
            title = title1,
            progress = progress
        )

        StatCard2 {
            Text(
                text = "How are you feeling today?",
                fontFamily = LatoFamily,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = primary_5,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val emojis = listOf("😊", "😐", "😔")
                val selectedIndex by viewModel.selectedEmojiIndex

                emojis.forEachIndexed { index, emoji ->
                    EmojiItem(
                        emoji = emoji,
                        isSelected = selectedIndex == index,
                        onClick = { viewModel.selectEmoji(index) }
                    )
                }
            }
        }
        val percentage = (progress * 100).toInt()
        StatCard1_3(
            current = percentage.toString(),
            total = "%",
            title = title3,
            progress = progress
        )
    }
}
@Composable
fun FilterSection(
    selectedTab: TimeTab,
    onTabClick: (TimeTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TimeTab.values().forEach { tab ->
            FilterButton(
                text = tab.name,
                isSelected = selectedTab == tab,
                onClick = { onTabClick(tab) }
            )
        }
    }
}

@Composable
fun TaskSection(
    tasks: List<Task>,
    onToggleStatus: (Task) -> Unit
) {
    val unfinishedTasks = tasks.filter { !it.isFinished }
    val finishedTasks = tasks.filter { it.isFinished }

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (unfinishedTasks.isNotEmpty()) {
            Text(
                text = "UNFINISHED - ${unfinishedTasks.size}",
                fontWeight = FontWeight.SemiBold,
                fontFamily = LatoFamily,
                fontSize = 14.sp,
                color = primary_5,
                modifier = Modifier.padding(start = 16.dp),
            )

            unfinishedTasks.forEach { task ->
                TaskUnfinishedCard(
                    icon = task.icon,
                    title = task.title,
                    goal = task.goal,
                    loopDisplay = task.loopDisplay,
                    streak = task.streak,
                    progress = task.progress,
                    onCheckChanged = { onToggleStatus(task) }
                )
            }
        }

        if (finishedTasks.isNotEmpty()) {
            Text(
                text = "FINISHED - ${finishedTasks.size}",
                fontWeight = FontWeight.SemiBold,
                fontFamily = LatoFamily,
                fontSize = 14.sp,
                color = primary_5,
                modifier = Modifier.padding(start = 16.dp),
            )

            finishedTasks.forEach { task ->
                TaskFinishedCard(
                    icon = task.icon,
                    title = task.title,
                    goal = task.goal,
                    loopDisplay = task.loopDisplay,
                    streak = task.streak,
                    progress = task.progress,
                    onCheckChanged = { onToggleStatus(task) }
                )
            }
        }
    }
}

@Composable
fun EmojiItem(emoji: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(if (isSelected) primary_3 else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = 18.sp)
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
fun PreviewHomeScreen() {
    val mockNavController = rememberNavController()
    val mockViewModel = HomeViewModel()

    LifeCycleOrganizerTheme {
        HomeScreen(
            navController = mockNavController,
            homeViewModel = mockViewModel
        )
    }
}