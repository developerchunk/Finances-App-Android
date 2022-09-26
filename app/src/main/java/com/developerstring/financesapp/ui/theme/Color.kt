package com.developerstring.financesapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightPurple = Color(0xFFC9D4FF)
val DarkerGray = Color(0xFF525252)
val LightGray = Color(0xFF636B7B)
val LighterGray = Color(0xFFB1B1B1)
val ExtraLightGray = Color(0xFFD9D9D9)
val LightDark = Color(0xFF20374D)
val Dark = Color(0xFF16212B)
val UIBlue = Color(0xFF1E90FF)
val LightBlue = Color(0xFF6CBEFF)
val UIWhite = Color(0xFFFFFFFF)

val Colors.textColorBW: Color
    @Composable
    get() = if (isLight) Black else White

val Colors.colorGray: Color
    @Composable
    get() = if (isLight) Gray else LightGray

val Colors.colorDarkGray: Color
    @Composable
    get() = if (isLight) ExtraLightGray else LightDark

val Colors.contentBackgroundColor: Color
    @Composable
    get() = if (isLight) Black else UIBlue

val Colors.backgroundColor: Color
    @Composable
    get() = if (isLight) White else Dark

val Colors.textColorBLG: Color
    @Composable
    get() = if (isLight) Black else LightGray