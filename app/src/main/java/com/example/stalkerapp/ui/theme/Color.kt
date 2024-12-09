package com.example.stalkerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val StalkerGreenDark = Color(0xFF0A1D1A)
val StalkerYellowDark = Color(0xFF8B6A2D)
val StalkerGrayDark = Color(0xFF333333)
val StalkerBlack = Color(0xFF212121)

// Світлі кольори для стилістики Сталкер
val StalkerGreenLight = Color(0xFF3A4D44)
val StalkerYellowLight = Color(0xFFD9A93D)
val StalkerGrayLight = Color(0xFFB0B0B0)
val StalkerWhite = Color(0xFFFCFCFC)


val StalkerGreenDarkForDarkTheme = Color(0xFF1D3A34)
val StalkerGrayDarkForDarkTheme = Color(0xFF4F4F4F)


val titleColorDark = Color(0xFF8C4E28)
val titleColorLight = Color(0xFFD9A93D)


val descriptionColorDark = Color(0xFF8B8B8B)
val descriptionColorLight = Color(0xFF6A7C7F)

val welcomeScreenBackground: Color
    @Composable
    get() = if (isSystemInDarkTheme()) StalkerGreenDark else StalkerGreenLight

val titleColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) titleColorDark else titleColorLight

val descriptionColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) descriptionColorDark else descriptionColorLight

val topBarTitleColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) titleColorDark else titleColorLight

val topBarBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) StalkerBlack else StalkerWhite