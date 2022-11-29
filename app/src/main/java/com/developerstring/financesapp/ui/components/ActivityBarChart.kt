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
import com.developerstring.financesapp.util.Constants.CURRENCY
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.simplifyAmount
import com.developerstring.financesapp.util.simplifyAmountIndia
import com.developerstring.financesapp.util.state.RoundTypeBarChart
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun ActivityBarChart(
    data: List<Float>,
    date: List<Int>,
    amount: List<Int>,
    height: Dp,
    roundType: RoundTypeBarChart,
    barWidth: Dp,
    barColor: Color,
    backBarColor: Color,
    barArrangement: Arrangement.Horizontal,
    point: Int,
) {
    val configuration = LocalConfiguration.current

    val width = configuration.screenWidthDp.dp

    val bottomSpacing = 40.dp

    val spacing by remember {
        mutableStateOf(100f)
    }

    val barShape =
        when (roundType) {
            RoundTypeBarChart.CIRCULAR_SHAPE -> CircleShape
            RoundTypeBarChart.TOP_CURVE -> RoundedCornerShape(topEnd = 3.dp, topStart = 3.dp)
        }

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = textColorBW.hashCode()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    val yCoordinates = mutableStateListOf<Float>()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {

        Column(
            modifier = Modifier
                .padding(top = bottomSpacing - 0.dp, end = 3.dp)
                .height(height)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Canvas(modifier = Modifier.fillMaxSize()) {

                val priceStep = (amount.max()) / 3f
                (0..3).forEach { i ->
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            if (CURRENCY == INDIAN_CURRENCY) simplifyAmountIndia(round(amount.min() + priceStep * i).roundToInt())
                            else simplifyAmount(round(amount.min() + priceStep * i).roundToInt()),
                            30f,
                            size.height - spacing - i * size.height / 3f,
                            textPaint
                        )
                    }
                    yCoordinates.add(size.height - spacing - i * size.height / 3f)
                }

                drawLine(
                    start = Offset(x = spacing + 30f, y = yCoordinates[1]),
                    end = Offset(x = size.width, y = yCoordinates[1]),
                    color = Color.Gray,
                    strokeWidth = 5f,
                    pathEffect = pathEffect
                )
                drawLine(
                    start = Offset(x = spacing + 30f, y = yCoordinates[2]),
                    end = Offset(x = size.width, y = yCoordinates[2]),
                    color = Color.Gray,
                    strokeWidth = 5f,
                    pathEffect = pathEffect
                )
                drawLine(
                    start = Offset(x = spacing + 30f, y = yCoordinates[3]),
                    end = Offset(x = size.width, y = yCoordinates[3]),
                    color = Color.Gray,
                    strokeWidth = 5f,
                    pathEffect = pathEffect
                )

            }

        }

        Box(
            modifier = Modifier
                .padding(start = 50.dp)
                .width(width - 100.dp)
                .height(height + bottomSpacing),
            contentAlignment = Alignment.BottomCenter
        ) {

            Row(
                modifier = Modifier
                    .width(width - 100.dp),
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

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
                                    .fillMaxHeight(animatedHeight)
                                    .background(barColor)
                            )
                        }

                        Column(
                            modifier = Modifier.height(
                                bottomSpacing
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {


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


                            Text(
                                modifier = Modifier
                                    .padding(
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
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(LightGreen)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                        }

                    }

                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 43.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(colorGray)
                )
            }


        }

    }
}
