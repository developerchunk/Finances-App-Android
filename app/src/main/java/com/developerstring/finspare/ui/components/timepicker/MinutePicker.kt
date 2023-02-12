package com.developerstring.finspare.ui.components.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developerstring.finspare.ui.theme.backgroundColorTimePicker
import com.developerstring.finspare.ui.theme.textColorBW

@Composable
fun MinutePicker(
    disable: Boolean,
    disableMinutes: List<Int>,
    screenWidth: Int,
    minutes: (Int) -> Unit,
) {

    val buttonSize = 6f

    val spacing = 10.dp

    val numbers = listOf(
        (1..3),
        (4..6),
        (7..9),
        (0..0),
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {

            numbers.forEach {
                MinutePickerButtons(
                    range = it,
                    disable = disable,
                    disableMinutes = disableMinutes,
                    size = (screenWidth / buttonSize).dp,
                    color = backgroundColorTimePicker,
                    disableColor = Color.Gray,
                    textColor = textColorBW,
                    spacing = spacing,
                    selected = { selected ->
                        minutes(selected)
                    }
                )
            }

        }


    }
}

@Composable
fun MinutePickerButtons(
    range: IntRange,
    disable: Boolean,
    disableMinutes: List<Int>,
    size: Dp,
    color: Color,
    disableColor: Color,
    textColor: Color = Color.Black,
    spacing: Dp,
    selected: (Int) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            spacing,
            alignment = Alignment.CenterHorizontally
        ),
    ) {
        (range).forEach {
            Card(
                modifier = Modifier
                    .size(size),
                shape = CircleShape,
                backgroundColor =
                if (disableMinutes.contains(it) && disable) disableColor.copy(alpha = 0.15f)
                else color,
                elevation = 0.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            if (disable && !disableMinutes.contains(it)) {
                                selected(it)
                            } else if (!disable) {
                                selected(it)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.toString(),
                        color = if (disableMinutes.contains(it) && disable) textColor.copy(alpha = 0.4f) else textColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }


    }


}











