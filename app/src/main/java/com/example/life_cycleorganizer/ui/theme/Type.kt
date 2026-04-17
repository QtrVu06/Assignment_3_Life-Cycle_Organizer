package com.example.life_cycleorganizer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.example.life_cycleorganizer.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Open Sans
val OpenSans = GoogleFont("Open Sans")

val OpenSansFamily = FontFamily(
    Font(googleFont = OpenSans, fontProvider = provider, weight = FontWeight.Bold),
)

// Lato
val Lato = GoogleFont("Lato")

val LatoFamily = FontFamily(
    Font(googleFont = Lato, fontProvider = provider, weight = FontWeight.Light),
    Font(googleFont = Lato, fontProvider = provider, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(googleFont = Lato, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = Lato, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = Lato, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = Lato, fontProvider = provider, weight = FontWeight.Medium, style = FontStyle.Italic),
)


val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = OpenSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = LatoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = LatoFamily,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp
    )
)