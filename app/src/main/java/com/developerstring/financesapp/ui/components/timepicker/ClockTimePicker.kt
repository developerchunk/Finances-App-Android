package com.developerstring.financesapp.ui.components.timepicker

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developerstring.financesapp.ui.theme.LighterUIBlue
import com.developerstring.financesapp.ui.theme.UIBlue

@Composable
fun ClockTimePicker(
    hour: Int,
    radius: Dp = 230.dp,
    format24: Boolean,
    selectedSize: Dp = 50.dp,
    selectedSize24: Dp = 40.dp,
    clickedHour: (Int) -> Unit,
) {

    val hours = 1..12

    val hours24 = 13..24

    val rotation = mutableListOf<Float>()

    hours.forEachIndexed { index, value ->
        rotation.add(index = index, element = ((360f / 12f) * value)+180f)
    }

    var selectedHour by remember {
        mutableStateOf(hour)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .size(radius+10.dp)
            .rotate(90f)
            .background(LighterUIBlue, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(5.dp)
                .background(UIBlue, shape = CircleShape)
        )


        if (format24) {
            hours24.forEachIndexed { index, value ->

                Box(
                    modifier = Modifier
                        .width(radius/1.6f)
                        .rotate(rotation[index]),
                    contentAlignment = Alignment.CenterEnd
                ) {

                    if (selectedHour == value) {
                        Box(
                            modifier = Modifier
                                .width((radius/2f) / 1.6f)
                                .padding(
                                    end = 20.dp,
                                )
                                .height(5.dp)
                                .background(color = UIBlue)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(selectedSize24),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(selectedSize24)
                                .clickable(indication = null, interactionSource = interactionSource) {
                                    selectedHour = value
                                    clickedHour(selectedHour)
                                },
                            contentAlignment = Alignment.Center,
                        ) {

                            val animatedSize by animateDpAsState(
                                targetValue = if (selectedHour == value) selectedSize24-10.dp else 0.dp,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearOutSlowInEasing
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .size(
                                        animatedSize
                                    )
                                    .background(
                                        color = UIBlue,
                                        shape = CircleShape
                                    )
                            )
                            Text(
                                modifier = Modifier
                                    .rotate(-rotation[index] - 90f),
                                text = value.toString(),
                                color = if (selectedHour==value) Color.White else Color.Black,
                                fontSize = 12.sp
                            )
                        }

                    }

                }
            }
        }

        hours.forEachIndexed { index, value ->

            Box(
                modifier = Modifier
                    .width(radius)
                    .rotate(rotation[index]),
                contentAlignment = Alignment.CenterEnd
            ) {

                if (selectedHour == value) {
                    Box(
                        modifier = Modifier
                            .width(radius / 2)
                            .padding(
                                end = 30.dp,
                            )
                            .height(5.dp)
                            .background(color = UIBlue)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(selectedSize),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(selectedSize)
                            .clickable(indication = null, interactionSource = interactionSource) {
                                selectedHour = value
                                clickedHour(selectedHour)
                            },
                        contentAlignment = Alignment.Center,
                    ) {

                        val animatedSize by animateDpAsState(
                            targetValue = if (selectedHour == value) selectedSize-10.dp else 0.dp,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        )
                        Box(
                            modifier = Modifier
                                .size(
                                    animatedSize
                                )
                                .background(
                                    color = UIBlue,
                                    shape = CircleShape
                                )
                        )
                        Text(
                            modifier = Modifier
                                .rotate(-rotation[index] - 90f),
                            text = value.toString(),
                            color = if (selectedHour==value) Color.White else Color.Black
                        )
                    }

                }

            }
        }


    }

}