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
val LightUIBlue = Color(0xFF4AA6FF)
val LightBlue = Color(0xFFB5DAFF)
val LighterBlue = Color(0xFFE4F5FF)
val Green = Color(0xFF04B6A5)
val LightGreen = Color(0xFF03DAC5)
val WhiteGreen = Color(0xFFB5FFF8)

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

val Colors.contentColorLBLD: Color
    @Composable
    get() = if (isLight) LighterBlue else LightDark

val Colors.greenIconColor: Color
    @Composable
    get() = if (isLight) Green else LightGreen

val Colors.lightGreenGraphColor: Color
    @Composable
    get() = if (isLight) WhiteGreen else WhiteGreen.copy(alpha = 0.3f)

val Colors.lightBlueGraphColor: Color
    @Composable
    get() = if (isLight) LightBlue else LightBlue.copy(alpha = 0.3f)