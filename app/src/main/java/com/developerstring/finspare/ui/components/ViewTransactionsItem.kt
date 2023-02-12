package com.developerstring.finspare.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.developerstring.finspare.roomdatabase.models.TransactionModel
import com.developerstring.finspare.ui.components.timepicker.Meridiem
import com.developerstring.finspare.ui.components.timepicker.stringToTime
import com.developerstring.finspare.ui.components.timepicker.timeConvert
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.Constants.OTHER
import com.developerstring.finspare.util.simplifyAmount
import com.developerstring.finspare.util.simplifyAmountIndia
import com.developerstring.finspare.util.transactionTypeToSymbol

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TransactionsItemView(
    transactionModel: TransactionModel,
    currency: String,
    navigateToDetails: (TransactionModel) -> Unit,
    time24Hours: Boolean
) {

//    val sameDate = TRANS == transactionModel.date

    val sameDate by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var hour by remember {
        mutableStateOf(0)
    }

    var minute by remember {
        mutableStateOf(0)
    }

    var meridiem by remember {
        mutableStateOf(Meridiem.HOUR24)
    }

    transactionModel.time.stringToTime(
        returnTime = {
            hour = it.first.toInt()
            minute = it.second.toInt()
        }
    )

    if (time24Hours) {
        meridiem = Meridiem.HOUR24
    } else {
        if (hour > 13) {
            hour -= 12
            meridiem = Meridiem.PM
        } else {
            meridiem = Meridiem.AM
        }
    }


    LaunchedEffect(key1 = true) {
        expanded = true
    }

    val extraInfoStatus = transactionModel.info != ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = scaleIn() + expandVertically(
                expandFrom = Alignment.CenterVertically,
                animationSpec = tween(durationMillis = 1000)
            ),

            ) {
            Surface(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = if (sameDate) 0.dp else 10.dp
                    )
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            navigateToDetails(transactionModel)
                        }),
                color = Color.Transparent,
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .alpha(if (sameDate) 0f else 1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {


                        Text(
                            text = transactionModel.day.toString(),
                            fontSize = TEXT_FIELD_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW
                        )
                        Text(
                            text = "${transactionModel.month}/${transactionModel.year}",
                            fontSize = EXTRA_SMALL_TEXT_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW
                        )

                        Text(
                            text = "${hour.timeConvert()}:${minute.timeConvert()}" +
                                    " ${if (meridiem == Meridiem.HOUR24) "" else meridiem.name}",
                            fontSize = SMALLEST_TEXT_SIZE,
                            fontFamily = fontOpenSans,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW
                        )


                    }

                    Surface(
                        elevation = 5.dp,
                        modifier = Modifier.padding(10.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = contentColorCard
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                                val (text, text1, text2, text3) = createRefs()

                                if (transactionModel.category!="") {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 20.dp)
                                            .fillMaxWidth(0.5f)
                                            .constrainAs(text) {
                                                start.linkTo(parent.start)
                                                top.linkTo(parent.top)
                                            },
                                        text =
                                        if (transactionModel.category == OTHER) transactionModel.categoryOther
                                        else transactionModel.category,
                                        fontFamily = fontInter,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = EXTRA_SMALL_TEXT_SIZE,
                                        color = colorGray,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 2
                                    )
                                }

                                Text(
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .fillMaxWidth(0.5f)
                                        .constrainAs(text1) {
                                            start.linkTo(parent.start)
                                            top.linkTo(text.bottom)
                                        },
                                    text =
                                    if (transactionModel.subCategory != "") {
                                        if (transactionModel.subCategory == OTHER) transactionModel.subCategoryOther
                                        else transactionModel.subCategory
                                    } else {
                                        if (transactionModel.transactionMode == OTHER) transactionModel.transactionModeOther
                                        else transactionModel.transactionMode
                                    },
                                    fontFamily = fontInter,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = TEXT_FIELD_SIZE,
                                    color = textColorBW,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 2,
                                )

                                if (extraInfoStatus) {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 20.dp)
                                            .constrainAs(text2) {
                                                start.linkTo(text1.start)
                                                top.linkTo(text1.bottom)
                                            },
                                        text = transactionModel.info,
                                        fontFamily = fontInter,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = EXTRA_SMALL_TEXT_SIZE,
                                        color = textColorBW,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Text(
                                    modifier = Modifier
                                        .padding(end = 20.dp)
                                        .constrainAs(text3) {
                                            top.linkTo(parent.top)
                                            end.linkTo(parent.end)
                                        },
                                    text = "${transactionTypeToSymbol(transactionType = transactionModel.transaction_type)} ${
                                        if (currency.last()
                                                .toString() == Constants.INDIAN_CURRENCY
                                        ) simplifyAmountIndia(
                                            transactionModel.amount
                                        ) else simplifyAmount(transactionModel.amount)
                                    } ${currency.last()}",
                                    fontFamily = fontInter,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = TEXT_FIELD_SIZE,
                                    color = textColorBW,
                                )

                            }

                            if (transactionModel.category != "" && transactionModel.subCategory !="") {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, top = 5.dp),
                                    text = "via: ${if (transactionModel.transactionMode == OTHER) transactionModel.transactionModeOther else transactionModel.transactionMode}",
                                    fontSize = SMALLEST_TEXT_SIZE,
                                    fontFamily = fontInter,
                                    fontWeight = FontWeight.Medium,
                                    color = textColorBW,
                                    textAlign = TextAlign.Start
                                )
                            }

                        }
                    }

                }

            }
        }
    }


}