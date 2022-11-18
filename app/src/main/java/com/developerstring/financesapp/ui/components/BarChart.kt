package com.developerstring.financesapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developerstring.financesapp.ui.theme.LightGreen
import com.developerstring.financesapp.ui.theme.UIBlue
import com.developerstring.financesapp.ui.theme.fontInter
import com.developerstring.financesapp.ui.theme.textColorBW

@Composable
fun BarChart(
    data: List<Float>,
    date: List<Int>,
    height: Dp
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .height(height),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            // graph
            data.forEach {

                var animationTriggered by remember {
                    mutableStateOf(false)
                }

                val animatedHeight by animateFloatAsState(
                    targetValue = if (animationTriggered) it else 0f,
                    animationSpec = tween(
                        durationMillis = 1000,
                        delayMillis = 0
                    )
                )

                LaunchedEffect(key1 = true) {
                    animationTriggered = true
                }

                Box(
                    modifier = Modifier
                        .padding(start = 14.dp, bottom = 5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .width(17.dp)
                        .fillMaxHeight(animatedHeight)
                        .background(UIBlue)
                )
            }

        }

        Row {
            date.forEach {
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(21.dp),
                    horizontalAlignment = CenterHorizontally
                ) {
                    Text(
                        text = it.toString(),
                        fontSize = 14.sp,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.textColorBW
                    )
                    if (it == date.last()) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(LightGreen)
                                .align(CenterHorizontally)
                        )
                    }
                }

            }
        }
    }
}