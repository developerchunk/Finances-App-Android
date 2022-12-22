package com.developerstring.financesapp.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.developerstring.financesapp.R
import com.developerstring.financesapp.sharedviewmodel.PublicSharedViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.CURRENCY
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.categorySortToText
import com.developerstring.financesapp.util.simplifyAmount
import com.developerstring.financesapp.util.simplifyAmountIndia
import com.developerstring.financesapp.util.state.CategorySortState
import com.developerstring.financesapp.util.textToCategorySort
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun PieChart(
    data: Map<String, Long>,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 1000,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()
    val dataPercentage = mutableListOf<Float>()
    var screenWidth by remember {
        mutableStateOf(0.dp)
    }

    val configuration = LocalConfiguration.current
    screenWidth = configuration.screenWidthDp.dp

    data.values.forEachIndexed { index, value ->
        floatValue.add(index, 360 * value.toFloat() / totalSum.toFloat())
        dataPercentage.add(index, ((value.toFloat() / totalSum.toFloat()) * 1000f))
    }

    val color = listOf(
        UIBlue.copy(alpha = 0.5f),
        UIBlue.copy(alpha = 1f),
        Green.copy(0.5f),
        Green.copy(1f),
        Yellow.copy(0.5f),
        Yellow.copy(1f),
        Orange.copy(0.5f),
        Orange.copy(1f),
        Pink.copy(0.5f),
        Pink.copy(1f),
        Purple500.copy(0.5f),
        Purple500.copy(1f),
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            Modifier.size((radiusOuter.value * 2f).dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(animateSize.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .size(radiusOuter * 2f)
                        .rotate(animateRotation)
                ) {
                    floatValue.forEachIndexed { index, value ->
                        if (value != 0f) {
                            drawArc(
                                color = color[index],
                                lastValue,
                                value,
                                useCenter = false,
                                style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt),
                            )
                            lastValue += value
                        }
                    }
                }
            }

        }

        AnimatedVisibility(
            visible = animationPlayed,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            DetailsPieChart(
                data = data,
                floatValue = dataPercentage,
                color = color,
                screenWidth = screenWidth,
            )
        }


        LaunchedEffect(key1 = true) {
            animationPlayed = true
        }

    }

}

@Composable
fun DetailsPieChart(
    data: Map<String, Long>,
    floatValue: List<Float>,
    color: List<Color>,
    screenWidth: Dp,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
    ) {

        data.entries.forEachIndexed { index, value ->

            PieChartDetailItem(
                data = value.toPair(),
                color = color[index],
                floatValue = floatValue[index],
                screenWidth = screenWidth
            )

        }

    }

}

@Composable
fun PieChartDetailItem(
    data: Pair<String, Long>,
    floatValue: Float,
    color: Color,
    boxSize: Dp = 35.dp,
    screenWidth: Dp
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(boxSize)
                .background(color, shape = RoundedCornerShape(10.dp))
                .border(
                    width = 2.dp,
                    color = color.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10.dp)
                )
        )

        Column(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .widthIn(max = screenWidth / 2f),
                    text = data.first,
                    fontFamily = fontInter,
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBW,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = (floatValue.toInt().toFloat() / 10f).toString() + "%",
                    fontSize = TEXT_FIELD_SIZE,
                    fontFamily = fontInter,
                    color = textColorBW
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = stringResource(id = R.string.spent) + ":",
                    fontFamily = fontInter,
                    fontSize = MEDIUM_TEXT_SIZE,
                    color = colorGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text =
                    if (CURRENCY == INDIAN_CURRENCY) simplifyAmountIndia(data.second.toInt())
                    else simplifyAmount(data.second.toInt()),
                    fontSize = MEDIUM_TEXT_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Bold,
                    color = colorGray
                )
            }

        }


    }

}