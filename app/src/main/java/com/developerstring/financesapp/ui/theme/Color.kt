package com.developerstring.financesapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import com.developerstring.financesapp.util.Constants.DARK_THEME_ENABLE

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Yellow = Color(0xFFFFC114)
val Orange = Color(0xFFFF5722)
val Pink = Color(0xFFFB2576)
val GreenBlue = Color(0xFF288BA8)
val LightishGreen = Color(0xFFDAF7A6)
val DarkYellow = Color(0xFF9A7D0A)
val Salmon = Color(0xFFFF8D85)
val Brown = Color(0xFF8F4700)
val DarkPink = Color(0xFFB619E0)
val DarkOrange = Color(0xFFD32E05)

val UIWhite = Color(0xFFF4F6F0)
val LightPurple = Color(0xFFC9D4FF)
val LightGray = Color(0xFF636B7B)
val SlightGray = Color(0xFF8D95A3)
val LighterGray = Color(0xFFB1B1B1)
val ExtraLightGray = Color(0xFFE4E7EC)
val LighterDark = Color(0xFF21415C)
val LightDark = Color(0xFF20374D)
val SlightDark = Color(0xFF1C2A36)
val Dark = Color(0xFF16212B)
val ExtraDark = Color(0xFF101922)
val UIBlue = Color(0xFF1E90FF)
val DarkBlue = Color(0xFF0067CC)
val DarkerBlue = Color(0xFF134064)
val LightUIBlue = Color(0xFF6CBEFF)
val LighterUIBlue = Color(0xFFDAF6FF)
val LightBlue = Color(0xFFB5DAFF)
val LightestBlue = Color(0xFFCCE6FF)
val LighterBlue = Color(0xFFE4F5FF)
val DarkGreen = Color(0xFF0C2B28)
val Green = Color(0xFF04B6A5)
val LightGreen = Color(0xFF03DAC5)
val WhiteGreen = Color(0xFFADFFF7)
val LightWhiteBlue = Color(0xFFF9FCFF)

val textColorBW: Color
    get() = if (!DARK_THEME_ENABLE) SlightDark else UIWhite

val colorGray: Color
    get() = if (!DARK_THEME_ENABLE) Gray else LighterGray

val colorLightGray: Color
    get() = if (!DARK_THEME_ENABLE) DarkGray else LighterGray

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

val backgroundColorTimePicker: Color
    get() = if (!DARK_THEME_ENABLE) LighterUIBlue else LightUIBlue.copy(alpha = 0.15f)

val textColorBLG: Color
    get() = if (!DARK_THEME_ENABLE) SlightDark else SlightGray

val contentColorLBLD: Color
    get() = if (!DARK_THEME_ENABLE) LighterUIBlue else DarkerBlue

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

val colorList = listOf(
    UIBlue,
    LightGreen,
    Yellow,
    Purple500,
    Orange
)

val COLORS_LIST_30 = listOf(
    UIBlue.copy(alpha = 0.5f),
    UIBlue.copy(alpha = 1f),
    Purple200.copy(0.5f),
    Purple200.copy(1f),
    Green.copy(0.5f),
    Green.copy(1f),
    Yellow.copy(0.5f),
    Yellow.copy(1f),
    Orange.copy(0.5f),
    Orange.copy(1f),
    Purple500.copy(0.5f),
    Purple500.copy(1f),
    Pink.copy(0.5f),
    Pink.copy(1f),
    Purple700.copy(0.5f),
    Purple700.copy(1f),
    GreenBlue.copy(0.5f),
    GreenBlue.copy(1f),
    LightishGreen.copy(0.5f),
    LightishGreen.copy(1f),
    DarkYellow.copy(0.5f),
    DarkYellow.copy(1f),
    Salmon.copy(0.5f),
    Salmon.copy(1f),
    Brown.copy(0.5f),
    Brown.copy(1f),
    DarkPink.copy(0.5f),
    DarkPink.copy(1f),
    DarkOrange.copy(0.5f),
    DarkOrange.copy(1f),
)