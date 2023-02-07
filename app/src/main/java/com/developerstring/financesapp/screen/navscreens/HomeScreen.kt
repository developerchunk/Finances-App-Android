package com.developerstring.financesapp.screen.navscreens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.navigation.navgraph.NavRoute
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.screen.navscreens.content.homescreen.MyActivityContent
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.PublicSharedViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.ActivityCardItems
import com.developerstring.financesapp.ui.components.DisplayAlertDialog
import com.developerstring.financesapp.ui.components.MessageBar
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.*
import com.developerstring.financesapp.util.Constants.ENGLISH
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.Constants.OTHER
import com.developerstring.financesapp.util.dataclass.ActivityCardData
import com.developerstring.financesapp.util.state.MessageBarState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    publicSharedViewModel: PublicSharedViewModel
) {

    val profileModel by profileViewModel.selectedProfile.collectAsState()

    val month = SimpleDateFormat("M", Locale(ENGLISH)).format(Date())
    val year = SimpleDateFormat("yyyy", Locale(ENGLISH)).format(Date())

    // get current month spending
    sharedViewModel.searchMonthSpent(
        month = month,
        year = year
    )
    // setArray
    sharedViewModel.setDayPaymentArray()

    // get current month savings
    sharedViewModel.searchMonthSavings(
        month = month,
        year = year
    )

    val totalSpent by sharedViewModel.monthSpent.collectAsState()
    val totalSavings by sharedViewModel.monthSavings.collectAsState()

    val scrollState = rememberScrollState()

    var spentPercent by remember {
        mutableStateOf(0f)
    }

    var savingsPercent by remember {
        mutableStateOf(0f)
    }

    spentPercent = (totalSpent.toFloat() / profileModel.month_spent.toFloat())
    savingsPercent = (totalSavings.toFloat() / profileModel.month_saving.toFloat())

    val day = SimpleDateFormat("d", Locale(ENGLISH)).format(Date()).toInt()

    var openDialog by remember {
        mutableStateOf(false)
    }

    var transactionModel by remember {
        mutableStateOf(TransactionModel())
    }

    val languageText = LanguageText(language = profileModel.language)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .verticalScroll(state = scrollState)
        ) {

            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TopGraphMainScreen(
                    total_amount = profileModel.total_amount,
                    spentPercent = if (spentPercent <= 1f) spentPercent else 1f,
                    savingPercent = if (savingsPercent <= 1f) savingsPercent else 1f,
                    currency = profileModel.currency.last().toString(),
                    text = stringResource(id = languageText.totalBalance)
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
                            text = if (profileModel.currency.last()
                                    .toString() == INDIAN_CURRENCY
                            ) simplifyAmountIndia(totalSpent.toInt()) else simplifyAmount(
                                totalSpent.toInt()
                            ),
                            fontSize = TEXT_FIELD_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = UIBlue,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.wrapContentSize(unbounded = true),
                            text = stringResource(id = languageText.spent),
                            fontSize = MEDIUM_TEXT_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW
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
                        colorFilter = ColorFilter.tint(greenIconColor)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            modifier = Modifier.wrapContentSize(unbounded = true),
                            text = if (profileModel.currency.last()
                                    .toString() == INDIAN_CURRENCY
                            ) simplifyAmountIndia(totalSavings.toInt()) else simplifyAmount(
                                totalSavings.toInt()
                            ),
                            fontSize = TEXT_FIELD_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = greenIconColor,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier.wrapContentSize(unbounded = true),
                            text = stringResource(id = languageText.saving),
                            fontSize = MEDIUM_TEXT_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW
                        )
                    }
                }
            }

            // Buttons
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 18.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                // Add Payment
                ActivityCardItems(
                    activityCardData = ActivityCardData(
                        text = languageText.addPayment,
                        icon = R.drawable.add_payment_icon,
                        bgColor = Dark,
                        cardColor = UIBlue,
                        key = ""
                    ),
                    size = Size(width = 155f, height = 160f),
                    imageCard = 60.dp,
                    cardCorner = 30.dp,
                    imageScale = ContentScale.FillBounds,
                    iconCardTopPadding = 16.dp,
                    onClick = {
                        navController.navigate(NavRoute.AddTransactionScreen.route)
                    }
                )
                // History
                ActivityCardItems(
                    activityCardData = ActivityCardData(
                        text = languageText.history,
                        icon = R.drawable.history_icon,
                        bgColor = DarkGreen,
                        cardColor = Green,
                        key = ""
                    ),
                    size = Size(width = 155f, height = 160f),
                    imageCard = 60.dp,
                    cardCorner = 30.dp,
                    imageScale = ContentScale.FillBounds,
                    iconCardTopPadding = 16.dp,
                    onClick = {
                        navController.navigate(NavRoute.ViewHistoryScreen.route)
                        // to get all transactions
                        sharedViewModel.getAllTransactions()
                    }
                )
            }
            MyActivityContent(
                sharedViewModel = sharedViewModel,
                day_ = day,
                month_ = month.toInt(),
                year_ = year.toInt(),
                currency = profileModel.currency.last().toString(),
                navController = navController,
                language = profileModel.language
            )
        }

        when (publicSharedViewModel.messageBarState.value) {
            MessageBarState.OPENED -> {

                sharedViewModel.getLastTransactionID()
                val lastTransactionID = sharedViewModel.lastTransactionID.collectAsState()

                MessageBarContent(
                    id = lastTransactionID.value!!,
                    publicSharedViewModel = publicSharedViewModel,
                    sharedViewModel = sharedViewModel
                ) {
                    transactionModel = it
                    openDialog = true
                }

            }
            else -> {
                publicSharedViewModel.messageBarState.value = MessageBarState.CLOSED
            }
        }

        DisplayAlertDialog(
            title =
            MessageBarContentLastTransaction().title(
                transactionType = transactionModel.transaction_type,
                currency = profileModel.currency,
                amount = transactionModel.amount
            ),

            message = MessageBarContentLastTransaction().message(
                category =
                if (transactionModel.category == OTHER)
                    transactionModel.categoryOther.take(20)
                else transactionModel.category,
                subCategory =
                if (transactionModel.subCategory == OTHER)
                    transactionModel.subCategoryOther.take(20)
                else transactionModel.subCategory,
                transactionMode =
                if (transactionModel.transactionMode == OTHER)
                    transactionModel.transactionModeOther
                else transactionModel.transactionMode,
                day = transactionModel.day,
                month = transactionModel.month,
                year = transactionModel.year
            ),
            openDialog = openDialog,
            onCloseClicked = {
                openDialog = false
            },
            onYesClicked = {
                publicSharedViewModel.messageBarState.value = MessageBarState.CLOSED
                sharedViewModel.transactionModel.value = transactionModel
                sharedViewModel.transactionAction(action = TransactionAction.DELETE)
            })
    }
}

@Composable
fun MessageBarContent(
    id: Int,
    publicSharedViewModel: PublicSharedViewModel,
    sharedViewModel: SharedViewModel,
    onClicked: (TransactionModel) -> Unit
) {

    sharedViewModel.getSelectedTransaction(transactionID = id)
    val getTransactionModel by sharedViewModel.selectedTransaction.collectAsState()

    getTransactionModel?.let {
        MessageBar(
            message =
            if (it.subCategory != "") {
                it.subCategory
            } else {
                if (it.category != "") it.category
                else {
                    if (it.transactionMode != "") it.transactionMode
                    else it.transactionModeOther
                }
            },
            publicSharedViewModel = publicSharedViewModel,
            action_type =
            if (it.transaction_type == "") {
                if (it.transactionMode == "") {
                    it.transactionModeOther
                } else {
                    it.transactionMode
                }
            } else {
                it.transaction_type.keyToTransactionType()
            }
        ) {
            onClicked(it)
        }
    }

}

@Composable
fun TopGraphMainScreen(
    total_amount: Int,
    spentPercent: Float,
    savingPercent: Float,
    radiusOuter: Dp = 100.dp,
    radiusInner: Dp = 80.dp,
    strokeWidth: Dp = 12.dp,
    animDuration: Int = 1000,
    currency: String,
    text: String
) {

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
                fontWeight = FontWeight.Medium,
                color = textColorBW
            )
            Text(
                text = text,
                fontSize = EXTRA_SMALL_TEXT_SIZE,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                color = textColorBW
            )
        }

        Canvas(modifier = Modifier.size(radiusOuter * 2)) {
            drawArc(
                color = lightBlueGraphColor,
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
                color = lightGreenGraphColor,
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












