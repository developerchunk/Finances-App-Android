package com.developerstring.financesapp.ui.components

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developerstring.financesapp.ui.theme.LightGreen
import com.developerstring.financesapp.ui.theme.colorGray
import com.developerstring.financesapp.ui.theme.fontInter
import com.developerstring.financesapp.ui.theme.textColorBW
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.simplifyAmount
import com.developerstring.financesapp.util.simplifyAmountIndia
import com.developerstring.financesapp.util.state.RoundTypeBarChart
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun ActivityBarChart(
    graphBarData: List<Float>,
    xAxisScaleData: List<Int>,
    amount: List<Int>,
    height: Dp,
    roundType: RoundTypeBarChart,
    barWidth: Dp,
    barColor: Color,
    backBarColor: Color,
    barArrangement: Arrangement.Horizontal,
    point: Int,
    point_size: Dp,
    currency: String
) {
    // for getting screen width and height you can use LocalConfiguration
    val configuration = LocalConfiguration.current
    // getting width of the screen
    val width = configuration.screenWidthDp.dp

    // bottom height of the X-Axis Scale
    val xAxisScaleHeight = 40.dp

    val yAxisScaleSpacing by remember {
        mutableStateOf(100f)
    }
    val yAxisTextWidth by remember {
        mutableStateOf(100.dp)
    }
    // Y-Axis scale values
    val yAxisValues = amount+0

    // Bar shape
    val barShape =
        when (roundType) {
            RoundTypeBarChart.CIRCULAR_SHAPE -> CircleShape
            RoundTypeBarChart.TOP_CURVE -> RoundedCornerShape(topEnd = 3.dp, topStart = 3.dp)
        }

    val density = LocalDensity.current
    // Y-Axis Scale Text Paint
    val textPaint = remember(density) {
        Paint().apply {
            color = textColorBW.hashCode()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    // for y coordinates of Y-Axis Scale to create horizontal dotted line indicating Y-Axis scale
    val yCoordinates = mutableStateListOf<Float>()
    // for dotted lines effects
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {

        // Y-Axis Scale and Horizontal dotted lines on Graph indicating Y-Axis Scales
        Column(
            modifier = Modifier
                .padding(top = xAxisScaleHeight - 0.dp, end = 3.dp)
                .height(height)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Canvas(modifier = Modifier.fillMaxSize()) {

                // Y-Axis Scale Text
                val yAxisScaleText = (yAxisValues.max()) / 3f
                (0..3).forEach { i ->
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            if (currency == INDIAN_CURRENCY) simplifyAmountIndia(round(yAxisValues.min() + yAxisScaleText * i).roundToInt())
                            else simplifyAmount(round(yAxisValues.min() + yAxisScaleText * i).roundToInt()),
                            30f,
                            size.height - yAxisScaleSpacing - i * size.height / 3f,
                            textPaint
                        )
                    }
                    yCoordinates.add(size.height - yAxisScaleSpacing - i * size.height / 3f)
                }

                // Horizontal dotted lines on Graph indicating Y-Axis Scales
                (1..3).forEach {
                    drawLine(
                        start = Offset(x = yAxisScaleSpacing + 30f, y = yCoordinates[it]),
                        end = Offset(x = size.width, y = yCoordinates[it]),
                        color = Color.Gray,
                        strokeWidth = 5f,
                        pathEffect = pathEffect
                    )
                }
            }

        }

        // Simple Graph with Bar Graphs and X-Axis Scale
        Box(
            modifier = Modifier
                .padding(start = 50.dp)
                .width(width - yAxisTextWidth)
                .height(height + xAxisScaleHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .width(width - yAxisTextWidth),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = barArrangement
            ) {
                // graph
                graphBarData.forEachIndexed { index, value ->

                    // animation will be triggered onStart and when the value changes
                    var animationTriggered by remember {
                        mutableStateOf(false)
                    }
                    val graphBarHeight by animateFloatAsState(
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Graph
                        Box(
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .clip(barShape)
                                .width(barWidth)
                                .height(height - 10.dp)
                                .background(backBarColor),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(barShape)
                                    .fillMaxWidth()
                                    .fillMaxHeight(graphBarHeight)
                                    .background(barColor)
                            )
                        }

                        // Scale X-Axis and Bottom part of Graph
                        Column(
                            modifier = Modifier.height(
                                xAxisScaleHeight
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            // small vertical line joining the horizontal X-Axis line
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 2.dp,
                                            bottomEnd = 2.dp
                                        )
                                    )
                                    .width(5.dp)
                                    .height(10.dp)
                                    .background(color = colorGray)
                            )
                            // scale X-Axis
                            Text(
                                modifier = Modifier
                                    .padding(
                                        bottom = 3.dp
                                    ),
                                text = xAxisScaleData[index].toString(),
                                fontSize = 14.sp,
                                fontFamily = fontInter,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = textColorBW,
                            )
                            // pointing a index on X-Axis
                            if (xAxisScaleData[index] == point) {
                                Box(
                                    modifier = Modifier
                                        .size(point_size)
                                        .clip(CircleShape)
                                        .background(LightGreen)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                        }

                    }

                }

            }

            // horizontal line on X-Axis below the Graph
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = xAxisScaleHeight + 3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(colorGray)
                )
            }


        }

    }
}
