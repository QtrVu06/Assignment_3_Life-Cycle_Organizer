package com.example.life_cycleorganizer.ui.settings

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.life_cycleorganizer.R
import com.example.life_cycleorganizer.ui.components.MyBottomBar
import com.example.life_cycleorganizer.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel
) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            MyBottomBar(
                navController = navController,
                selectedScreen = "Settings"
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
                        color = primary_5,
                    ),
            ) {
                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = "Settings",
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
                    text = "Personalize the Experience",
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

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "LANGUAGE",
                    style = MaterialTheme.typography.labelLarge,
                    color = primary_5,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
                LanguageSectionDisabled() // Giao diện mờ

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "FOCUS TIMER",
                    style = MaterialTheme.typography.labelLarge,
                    color = primary_5,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
                FocusTimerSettings(viewModel)
            }

            Text(
                text = "Life-Cycle Organizer v1.0.0 - Created by QtrV",
                modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = neutral_4,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun LanguageSectionDisabled() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = neutral_2.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, neutral_3)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Option English
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.alpha(0.4f)) {
                Box(modifier = Modifier.size(20.dp).background(neutral_4, CircleShape))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("GB English", fontWeight = FontWeight.Bold, color = primary_5)
                    Text("English (US)", fontSize = 12.sp, color = neutral_4)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = neutral_3)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.alpha(0.4f)) {
                Box(modifier = Modifier.size(20.dp).border(2.dp, neutral_4, CircleShape))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("VN Tiếng Việt", fontWeight = FontWeight.Bold, color = primary_5)
                    Text("Vietnamese", fontSize = 12.sp, color = neutral_4)
                }
            }
        }
    }
}

@Composable
fun FocusTimerSettings(viewModel: SettingsViewModel) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = neutral_2,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SettingRowWithDropdown(
                label = "Focus Time Default",
                selectedOption = viewModel.selectedFocusDefault,
                options = viewModel.focusDefaultOptions,
                onOptionSelected = { viewModel.selectedFocusDefault = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = neutral_3)

            SettingRowWithDropdown(
                label = "Rest Time Default",
                selectedOption = viewModel.selectedRestDefault,
                options = viewModel.restDefaultOptions,
                onOptionSelected = { viewModel.selectedRestDefault = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = neutral_3)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Alarm When Finished", color = primary_5, fontFamily = LatoFamily, fontWeight = FontWeight.Medium)
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = viewModel.isAlarmEnabled,
                    onCheckedChange = { viewModel.isAlarmEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = neutral_1, checkedTrackColor = primary_3)
                )
            }
        }
    }
}

@Composable
fun SettingRowWithDropdown(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontFamily = LatoFamily, color = primary_5, fontWeight = FontWeight.Medium)
        Spacer(Modifier.weight(1f))
        Box {
            Row(
                modifier = Modifier.clickable { expanded = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(selectedOption, fontFamily = LatoFamily, color = primary_5, modifier = Modifier.padding(horizontal = 12.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_down),
                    contentDescription = null,
                    tint = primary_5)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
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
fun PreviewSettingsScreen() {

    val mockNavController = rememberNavController()
    val mockViewModel = SettingsViewModel()

    LifeCycleOrganizerTheme {
        SettingsScreen(
            navController = mockNavController,
            viewModel = mockViewModel
        )
    }
}