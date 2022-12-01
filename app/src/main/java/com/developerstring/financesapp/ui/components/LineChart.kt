package com.developerstring.financesapp.ui.components

import android.graphics.Paint
import android.graphics.Path
import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.CURRENCY
import com.developerstring.financesapp.util.Constants.DARK_THEME_ENABLE
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.dataclass.LineChartData
import com.developerstring.financesapp.util.simplifyAmount
import com.developerstring.financesapp.util.simplifyAmountIndia
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun LineChart(
    infos: List<LineChartData> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green,
    textColor: Int
) {

    val yCoordinates = mutableStateListOf<Float>()

    val spacing = 100f

    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.amount }?.plus(1))?.roundToInt() ?: 0
    }
    val lowerValue = remember(infos) {
        infos.minOfOrNull { it.amount }?.toInt() ?: 0
    }
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = textColor
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    var animate by remember {
        mutableStateOf(false)
    }
    var canvasHeight by remember {
        mutableStateOf(0f)
    }
    val animatedHeight by animateFloatAsState(
        targetValue = if (animate) canvasHeight else 0f,
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutLinearInEasing
        )
    )
    val transparentGraphColor = remember {
        Animatable(if (DARK_THEME_ENABLE) WhiteGreen else LightBlue)
    }

    var offsetX by remember {
        mutableStateOf(0f)
    }
    var offsetY by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(key1 = true) {
        animate = true
        transparentGraphColor.animateTo(
            graphColor,
            tween(2000)
        )
    }

    Column(modifier = modifier) {

        Text(text = offsetX.toString(), color = textColorBW)

        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)) {

            canvasHeight = size.height

            val spacePerHour = (size.width - spacing) / infos.size
            (0 until infos.size - 1 step 7).forEach { i ->
                val info = infos[i]
                val hour = info.date
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        hour.toString(),
                        spacing + i * spacePerHour,
                        size.height - 5,
                        textPaint
                    )
                }
            }
            val priceStep = (upperValue) / 3f
            (0..3).forEach { i ->
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        if (CURRENCY == INDIAN_CURRENCY) simplifyAmountIndia(round(lowerValue + priceStep * i).roundToInt())
                        else simplifyAmount(round(lowerValue + priceStep * i).roundToInt()),
                        30f,
                        size.height - spacing - i * size.height / 3.4f,
                        textPaint
                    )
                }
                yCoordinates.add(size.height - spacing - i * size.height / 3.4f)
            }

            var lastX = 0f
            val strokePath = androidx.compose.ui.graphics.Path().apply {
                for (i in infos.indices) {
                    val info = infos[i]
                    val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
                    val leftRatio = (info.amount - lowerValue) / (upperValue - lowerValue)
                    val rightRatio =
                        (nextInfo.amount - lowerValue) / (upperValue - lowerValue)

                    val x1 = spacing + i * spacePerHour
                    val y1 = animatedHeight - spacing - (leftRatio * animatedHeight).toFloat()
                    val x2 = spacing + (i + 1) * spacePerHour
                    val y2 = animatedHeight - spacing - (rightRatio * animatedHeight).toFloat()
                    if (i == 0) {
                        moveTo(x1, y1)
                    }
                    lastX = (x1 + x2) / 2f
                    quadraticBezierTo(
                        x1, y1, lastX, (y1 + y2) / 2f
                    )

                    offsetX = y2

                }
            }
            val fillPath = Path(strokePath.asAndroidPath())
                .asComposePath()
                .apply {
                    lineTo(lastX, size.height - spacing)
                    lineTo(spacing, size.height - spacing)
                    close()
                }

            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            drawLine(
                start = Offset(x = spacing, y = yCoordinates[1]),
                end = Offset(x = size.width, y = yCoordinates[1]),
                color = Color.Gray,
                strokeWidth = 5f,
                pathEffect = pathEffect
            )
            drawLine(
                start = Offset(x = spacing, y = yCoordinates[2]),
                end = Offset(x = size.width, y = yCoordinates[2]),
                color = Color.Gray,
                strokeWidth = 5f,
                pathEffect = pathEffect
            )
            drawLine(
                start = Offset(x = spacing, y = yCoordinates[3]),
                end = Offset(x = size.width, y = yCoordinates[3]),
                color = Color.Gray,
                strokeWidth = 5f,
                pathEffect = pathEffect
            )
            // graph
            drawLine(
                start = Offset(x = spacing - 20f, y = size.height - spacing),
                end = Offset(x = size.width, y = size.height - spacing),
                color = Color.Gray,
                strokeWidth = 5f,
            )
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        transparentGraphColor.value,
                        backgroundColor
                    ),
                    endY = size.height - spacing
                ),
            )
            drawPath(
                path = strokePath,
                color = transparentGraphColor.value,
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
    }

}