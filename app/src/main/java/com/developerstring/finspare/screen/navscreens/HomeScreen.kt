package com.developerstring.finspare.screen.navscreens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.roomdatabase.models.TransactionModel
import com.developerstring.finspare.screen.transaction.settleDeleteTransaction
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.components.ActivityCardItems
import com.developerstring.finspare.ui.components.DisplayAlertDialog
import com.developerstring.finspare.ui.components.MessageBar
import com.developerstring.finspare.ui.components.MyActivityContent
import com.developerstring.finspare.ui.theme.Dark
import com.developerstring.finspare.ui.theme.DarkGreen
import com.developerstring.finspare.ui.theme.EXTRA_LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.EXTRA_SMALL_TEXT_SIZE
import com.developerstring.finspare.ui.theme.Green
import com.developerstring.finspare.ui.theme.LightGreen
import com.developerstring.finspare.ui.theme.MEDIUM_TEXT_SIZE
import com.developerstring.finspare.ui.theme.SMALL_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.UIBlue
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.colorGray
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.greenIconColor
import com.developerstring.finspare.ui.theme.lightBlueGraphColor
import com.developerstring.finspare.ui.theme.lightGreenGraphColor
import com.developerstring.finspare.ui.theme.messageToPaymentCard
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants.ENGLISH
import com.developerstring.finspare.util.Constants.INDIAN_CURRENCY
import com.developerstring.finspare.util.Constants.OTHER
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.MessageBarContentLastTransaction
import com.developerstring.finspare.util.TransactionAction
import com.developerstring.finspare.util.dataclass.ActivityCardData
import com.developerstring.finspare.util.keyToTransactionType
import com.developerstring.finspare.util.simplifyAmount
import com.developerstring.finspare.util.simplifyAmountIndia
import com.developerstring.finspare.util.state.MessageBarState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    LaunchedEffect(key1 = true) {
        profileViewModel.getProfileDetails()
    }

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
        mutableFloatStateOf(0f)
    }

    var savingsPercent by remember {
        mutableFloatStateOf(0f)
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

            Row(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {

                TopGraphMainScreen(
                    total_amount = profileModel.total_amount,
                    spentPercent = if (spentPercent <= 1f) spentPercent else 1f,
                    savingPercent = if (savingsPercent <= 1f) savingsPercent else 1f,
                    currency = profileModel.currency.last().toString(),
                    text = stringResource(id = languageText.totalBalance),
                    navController = navController
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
                        sharedViewModel.messageID.value = ""
                        sharedViewModel.addTransactionModel.value = TransactionModel()
                        navController.navigate(NavRoute.AddTransactionScreen.route)
                    }
                )
                // History
                ActivityCardItems(
                    activityCardData = ActivityCardData(
                        text = languageText.paymentHistory,
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

            // auto pay button
            Card(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .clickable {
                               navController.navigate(NavRoute.AutoPaymentScreen.route)
                    }
                ,
                shape = RoundedCornerShape(20.dp),
                backgroundColor = messageToPaymentCard
            ) {

                    Column(modifier = Modifier.padding(15.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Add Payments directly from \nBank Messages you receive",
                                fontSize = MEDIUM_TEXT_SIZE,
                                color = textColorBW,
                                fontFamily = fontInter,
                                fontWeight = FontWeight.Normal
                            )

                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(LightGreen)
                                    .size(10.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxWidth()
                        )

                        Text(
                            text = "Made an online payment? Add Payments Now! introducing effortless budgeting: Bank Messanges to Payments",
                            fontSize = SMALL_TEXT_SIZE,
                            color = colorGray,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Normal
                        )
                    }


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
                    sharedViewModel = sharedViewModel,
                    profileViewModel = profileViewModel
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
            languageText = languageText,
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
                profileViewModel.saveTotalAmount(
                    amount = settleDeleteTransaction(
                        transactionType = transactionModel.transaction_type,
                        totalAmount = profileModel.total_amount,
                        oldAmount = transactionModel.amount
                    )
                )
                publicSharedViewModel.messageBarState.value = MessageBarState.CLOSED
                sharedViewModel.transactionModel.value = transactionModel
                sharedViewModel.transactionAction(
                    action = TransactionAction.DELETE,
                    id = transactionModel.id
                )
            })
    }
}

@Composable
fun MessageBarContent(
    id: Int,
    publicSharedViewModel: PublicSharedViewModel,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    onClicked: (TransactionModel) -> Unit
) {

    sharedViewModel.getSelectedTransaction(transactionID = id)
    val getTransactionModel by sharedViewModel.selectedTransaction.collectAsState()
    val selectedContact by profileViewModel.selectedContact.collectAsState()


    getTransactionModel?.let {
        MessageBar(
            message =
            if (it.amount_type.isNotEmpty()) {
                selectedContact.name
            } else {
                if (it.subCategory != "") {
                    it.subCategory
                } else {
                    if (it.category != "") it.category
                    else {
                        if (it.transactionMode != "") it.transactionMode
                        else it.transactionModeOther
                    }
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
            },
            profileAmountType = it.amount_type
        ) {
            onClicked(it)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
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
    text: String,
    navController: NavController
) {

    var animationPlayed by remember { mutableStateOf(false) }
    val spentCurPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) spentPercent else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0
        ), label = ""
    )
    val savingsCurPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) savingPercent else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0
        ), label = ""
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .size(radiusOuter * 2),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.combinedClickable(
                onClick = {
                    navController.navigate(NavRoute.AmountChartScreen.route)
                },
                onLongClick = {
                    navController.navigate(NavRoute.EditProfileScreen.route)
                }
            )) {
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












