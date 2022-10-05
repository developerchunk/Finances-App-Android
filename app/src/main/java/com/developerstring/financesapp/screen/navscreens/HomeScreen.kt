package com.developerstring.financesapp.screen.navscreens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.navigation.navgraph.NavRoute
import com.developerstring.financesapp.screen.navscreens.content.homescreen.MyActivityContent
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.simplifyAmount
import com.developerstring.financesapp.util.simplifyAmountIndia
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel
) {
    val totalAmount by profileViewModel.profileTotalAmount.collectAsState()
    val spent by profileViewModel.profileSpending.collectAsState()
    val savings by profileViewModel.profileSavings.collectAsState()
    val currency by profileViewModel.profileCurrency.collectAsState()

    val totalSpent by sharedViewModel.monthSpent.collectAsState()
    val totalSavings by sharedViewModel.monthSavings.collectAsState()

    var spentPercent by remember {
        mutableStateOf(0f)
    }

    var savingsPercent by remember {
        mutableStateOf(0f)
    }

    val buttonColor = Brush.horizontalGradient(colors = listOf(UIBlue, LightUIBlue))

    val spentSum = totalSpent.sum()
    val savingsSum = totalSavings.sum()

    spentPercent = (spentSum.toFloat() / spent.toFloat())
    savingsPercent = (savingsSum.toFloat() / savings.toFloat())

    val day = SimpleDateFormat("d").format(Date()).toInt()
    val month = SimpleDateFormat("M").format(Date()).toInt()
    val year = SimpleDateFormat("yyyy").format(Date()).toInt()

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundColor)
            .padding(top = 30.dp)

    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TopGraphMainScreen(
                total_amount = totalAmount,
                spentPercent = if (spentPercent <= 1f) spentPercent else 1f,
                savingPercent = if (savingsPercent <= 1f) savingsPercent else 1f,
                currency = currency.last().toString()
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.padding(end = 20.dp)) {
                Image(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.spent_icon),
                    contentDescription = "spent_icon",
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.wrapContentSize(unbounded = true),
                        text = if (currency == INDIAN_CURRENCY) simplifyAmountIndia(spentSum) else simplifyAmount(
                            spentSum
                        ),
                        fontSize = MEDIUM_TEXT_SIZE,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        color = UIBlue,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.wrapContentSize(unbounded = true),
                        text = stringResource(id = R.string.spent),
                        fontSize = MEDIUM_TEXT_SIZE,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.textColorBW
                    )
                }
            }

            Row(modifier = Modifier.padding(start = 20.dp)) {
                Image(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.saving_icon),
                    contentDescription = "saving_icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.greenIconColor)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.wrapContentSize(unbounded = true),
                        text = if (currency == INDIAN_CURRENCY) simplifyAmountIndia(savingsSum) else simplifyAmount(
                            savingsSum
                        ),
                        fontSize = MEDIUM_TEXT_SIZE,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.greenIconColor,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.wrapContentSize(unbounded = true),
                        text = stringResource(id = R.string.saving),
                        fontSize = MEDIUM_TEXT_SIZE,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.textColorBW
                    )
                }
            }
        }

        // Buttons
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Add Payment
            Box(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(160.dp)
                    .height(38.dp)
                    .background(brush = buttonColor, shape = RoundedCornerShape(20.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            navController.navigate(NavRoute.AddTransactionScreen.route)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.add_payment),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = MEDIUM_TEXT_SIZE,
                    color = Color.White
                )
            }
            // History
            Box(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .width(160.dp)
                    .height(38.dp)
                    .background(brush = buttonColor, shape = RoundedCornerShape(20.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            navController.navigate(NavRoute.ViewHistoryScreen.route)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.history),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = MEDIUM_TEXT_SIZE,
                    color = Color.White
                )
            }
        }
        MyActivityContent(sharedViewModel = sharedViewModel, day_ = day, month_ = month, year_ = year)
    }
}

@Composable
fun TopGraphMainScreen(
    total_amount: Int,
    spentPercent: Float,
    savingPercent: Float,
    radiusOuter: Dp = 90.dp,
    radiusInner: Dp = 70.dp,
    strokeWidth: Dp = 12.dp,
    animDuration: Int = 1000,
    currency: String
) {

    val lightGreenColor: MutableState<Color> =
        mutableStateOf(MaterialTheme.colors.lightGreenGraphColor)
    val lightBlueColor: MutableState<Color> =
        mutableStateOf(MaterialTheme.colors.lightBlueGraphColor)

    var animationPlayed by remember { mutableStateOf(false) }
    val spentCurPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) spentPercent else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0
        )
    )
    val savingsCurPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) savingPercent else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .size(radiusOuter * 2),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$currency " +
                        if (currency == INDIAN_CURRENCY) simplifyAmountIndia(amount = total_amount)
                        else simplifyAmount(amount = total_amount),
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = stringResource(id = R.string.total_balance),
                fontSize = EXTRA_SMALL_TEXT_SIZE,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium
            )
        }

        Canvas(modifier = Modifier.size(radiusOuter * 2)) {
            drawArc(
                color = lightBlueColor.value,
                -90f,
                360f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx())
            )
            drawArc(
                color = UIBlue,
                -90f,
                360 * spentCurPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Canvas(modifier = Modifier.size(radiusInner * 2)) {
            drawArc(
                color = lightGreenColor.value,
                -90f,
                360f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx())
            )
            drawArc(
                color = LightGreen,
                -90f,
                360 * savingsCurPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}