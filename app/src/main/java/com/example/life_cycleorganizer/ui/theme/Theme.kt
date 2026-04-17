package com.example.life_cycleorganizer.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.material3.Typography

private val LightColorScheme = lightColorScheme(
    primary = primary_5,
    secondary = primary_1,
    background = neutral_1,
    surface = neutral_1,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onBackground = neutral_5,
    onSurface = neutral_5
)

@Composable
fun LifeCycleOrganizerTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        window.statusBarColor = primary_5.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}