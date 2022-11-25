package com.developerstring.financesapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import com.developerstring.financesapp.util.Constants.DARK_THEME_ENABLE

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightPurple = Color(0xFFC9D4FF)
val LightGray = Color(0xFF636B7B)
val SlightGray = Color(0xFF8D95A3)
val LighterGray = Color(0xFFB1B1B1)
val ExtraLightGray = Color(0xFFE4E7EC)
val LightDark = Color(0xFF20374D)
val SlightDark = Color(0xFF1C2A36)
val Dark = Color(0xFF16212B)
val ExtraDark = Color(0xFF131E29)
val UIBlue = Color(0xFF1E90FF)
val LightUIBlue = Color(0xFF6CBEFF)
val LightBlue = Color(0xFFB5DAFF)
val LighterBlue = Color(0xFFE4F5FF)
val Green = Color(0xFF04B6A5)
val LightGreen = Color(0xFF03DAC5)
val WhiteGreen = Color(0xFFADFFF7)
val LightWhiteBlue = Color(0xFFF9FCFF)

val textColorBW: Color
    get() = if (!DARK_THEME_ENABLE) Black else White

val colorGray: Color
    get() = if (!DARK_THEME_ENABLE) Gray else LighterGray

val colorDarkGray: Color
    get() = if (!DARK_THEME_ENABLE) ExtraLightGray else LightDark

val contentBackgroundColor: Color
    get() = if (!DARK_THEME_ENABLE) Black else UIBlue

val backgroundColor: Color
    get() = if (!DARK_THEME_ENABLE) LightWhiteBlue else Dark

val backgroundColorBW: Color
    get() = if (!DARK_THEME_ENABLE) White else ExtraDark

val backgroundColorCard: Color
    get() = if (!DARK_THEME_ENABLE) White else LightGray

val textColorBLG: Color
    get() = if (!DARK_THEME_ENABLE) Black else SlightGray

val contentColorLBLD: Color
    get() = if (!DARK_THEME_ENABLE) LighterBlue else LightDark

val contentColorCard: Color
    get() = if (!DARK_THEME_ENABLE) White else LightDark

val contentColorLBSD: Color
    get() = if (!DARK_THEME_ENABLE) LighterBlue else SlightDark

val greenIconColor: Color
    get() = if (!DARK_THEME_ENABLE) Green else LightGreen

val lightGreenGraphColor: Color
    get() = if (!DARK_THEME_ENABLE) WhiteGreen else WhiteGreen.copy(alpha = 0.3f)

val lightBlueGraphColor: Color
    get() = if (!DARK_THEME_ENABLE) LightBlue else LightBlue.copy(alpha = 0.3f)