package com.developerstring.financesapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.state.RoundTypeBarChart

@Composable
fun BarChart(
    data: List<Float>,
    date: List<Int>,
    height: Dp,
    roundType: RoundTypeBarChart,
    barWidth: Dp,
    barColor: Color,
    backBarColor: Color,
    alignment: Alignment.Horizontal,
    barArrangement: Arrangement.Horizontal,
    point: Int,
) {

    val barShape =
        when (roundType) {
            RoundTypeBarChart.CIRCULAR_SHAPE -> CircleShape
            RoundTypeBarChart.TOP_CURVE -> RoundedCornerShape(topEnd = 3.dp, topStart = 3.dp)
        }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height + 30.dp),
            contentAlignment = BottomCenter
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = barArrangement
            ) {
                // graph
                data.forEachIndexed { index, value ->

                    var animationTriggered by remember {
                        mutableStateOf(false)
                    }

                    val animatedHeight by animateFloatAsState(
                        targetValue = if (animationTriggered) value else 0f,
                        animationSpec = tween(
                            durationMillis = 1000,
                            delayMillis = 0
                        )
                    )

                    LaunchedEffect(key1 = true) {
                        animationTriggered = true
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(start = 14.dp, bottom = 5.dp)
                                .clip(barShape)
                                .width(barWidth)
                                .height(height - 10.dp)
                                .background(backBarColor),
                            contentAlignment = BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(barShape)
                                    .fillMaxWidth()
                                    .fillMaxHeight(animatedHeight)
                                    .background(barColor)
                            )
                        }

                        Column(
                            modifier = Modifier.height(30.dp),
                            horizontalAlignment = CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 14.dp,
                                        bottom = 3.dp
                                    ),
                                text = date[index].toString(),
                                fontSize = 14.sp,
                                fontFamily = fontInter,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = textColorBW,
                            )
                            if (date[index] == point) {
                                Box(
                                    modifier = Modifier
                                        .padding(start = 14.dp)
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

    }
}