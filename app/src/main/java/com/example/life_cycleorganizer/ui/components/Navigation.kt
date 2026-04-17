package com.example.life_cycleorganizer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.life_cycleorganizer.R
import com.example.life_cycleorganizer.ui.theme.LatoFamily
import com.example.life_cycleorganizer.ui.theme.primary_1
import com.example.life_cycleorganizer.ui.theme.primary_5

@Composable
fun MyBottomBar(
    navController: NavController,
    selectedScreen: String,
    currentRoute: String = "Home",
    onTabClick: (String) -> Unit = {}
) {
    val tabs = listOf(
        NavigationItem("Home", R.drawable.ic_home, R.drawable.ic_bold_home),
        NavigationItem("Focus", R.drawable.ic_focus, R.drawable.ic_bold_focus),
        NavigationItem("Sleep", R.drawable.ic_sleep, R.drawable.ic_bold_sleep),
        NavigationItem("Settings", R.drawable.ic_settings, R.drawable.ic_bold_settings)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 12.dp, end = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp, // Drop shadow Y4
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                )
                .size(width = 336.dp, height = 56.dp) // Kích thước chuẩn Figma
                .background(color = primary_1, shape = RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEach { item ->
                    val isSelected = selectedScreen == item.route

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {

                                val route = when (item.route) {
                                    "Home" -> "home"
                                    "Focus" -> "focus"
                                    "Sleep" -> "sleep"
                                    "Settings" -> "settings"
                                    else -> "home"
                                }

                                navController.navigate(route) {
                                    popUpTo("home")
                                    launchSingleTop = true
                                }
                            }
                            .padding(bottom = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isSelected) item.boldIcon else item.normalIcon
                            ),
                            contentDescription = item.route,
                            tint = primary_5,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer {
                                    translationY = if (isSelected) -4.dp.toPx() else 0f
                                }
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = item.route,
                            color = primary_5,
                            fontSize = 12.sp,
                            fontFamily = LatoFamily,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

data class NavigationItem(
    val route: String,
    val normalIcon: Int,
    val boldIcon: Int
)