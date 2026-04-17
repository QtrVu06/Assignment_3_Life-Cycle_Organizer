package com.example.life_cycleorganizer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.life_cycleorganizer.ui.home.FocusScreen
import com.example.life_cycleorganizer.ui.home.HomeScreen
import com.example.life_cycleorganizer.ui.home.HomeViewModel
import com.example.life_cycleorganizer.ui.home.NewHabitScreen
import com.example.life_cycleorganizer.ui.settings.SettingsScreen
import com.example.life_cycleorganizer.ui.settings.SettingsViewModel
import com.example.life_cycleorganizer.ui.sleep.SleepScreen
import com.example.life_cycleorganizer.ui.theme.LifeCycleOrganizerTheme
import com.example.life_cycleorganizer.ui.focus.FocusViewModel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            LifeCycleOrganizerTheme {

                val navController = rememberNavController()

                val homeViewModel: HomeViewModel = viewModel()
                val settingsViewModel: SettingsViewModel = viewModel()
                val focusViewModel: FocusViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {

                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            homeViewModel = homeViewModel
                        )
                    }

                    composable("new_habit_screen") {
                        NewHabitScreen(
                            onBack = { navController.popBackStack() },
                            homeViewModel = homeViewModel
                        )
                    }

                    composable("focus") {
                        FocusScreen(
                            navController = navController,
                            focusViewModel = focusViewModel,
                            settingsViewModel = settingsViewModel
                        )
                    }

                    composable("sleep") {
                        SleepScreen(
                            navController = navController
                        )
                    }

                    composable("settings") {
                        SettingsScreen(
                            navController = navController,
                            viewModel = settingsViewModel
                        )
                    }
                }
            }
        }
    }
}