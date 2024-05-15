package com.developerstring.finspare.screen.autopayment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developerstring.finspare.ui.theme.EXTRA_SMALL_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.greenIconColor
import com.developerstring.finspare.ui.theme.textBoxBackColor
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.simplifyAmount
import com.developerstring.finspare.util.simplifyAmountIndia
import com.developerstring.finspare.util.transactionTypeToSymbol

data class TimeValues(
    val day: Short,
    val month: Short,
    val year: Short,
)

@Composable
fun MessageItemView(
    bankTransaction: BankTransaction,
    currency: String,
    navigateToDetails: () -> Unit,
    launched: Boolean,
    expandedLaunched: Boolean,
    message: String,
    checked: Boolean
) {

//    val sameDate = TRANS == transactionModel.date

    val sameDate by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var expanded by remember {
        mutableStateOf(expandedLaunched)
    }

    val timeValues = bankTransaction.timeValues


    LaunchedEffect(key1 = true) {
        expanded = launched
    }

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
                            navigateToDetails()
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
                            text = timeValues.day.toString(),
                            fontSize = TEXT_FIELD_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW
                        )
                        Text(
                            text = "${timeValues.month}/${timeValues.year}",
                            fontSize = EXTRA_SMALL_TEXT_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW
                        )

                        if (checked) {
                            Icon(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .width(28.dp)
                                    .height(24.dp),
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = "payment of ${bankTransaction.amount} $currency is already added already",
                                tint = greenIconColor
                            )
                        }

                    }

                    Surface(
                        modifier = Modifier.padding(10.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = textBoxBackColor
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                modifier = Modifier
                                    .weight(6f)
                                    .padding(start = 20.dp),
                                text = message,
                                fontFamily = fontInter,
                                fontWeight = FontWeight.Medium,
                                fontSize = EXTRA_SMALL_TEXT_SIZE,
                                color = textColorBW,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                modifier = Modifier
                                    .weight(4f)
                                    .padding(end = 20.dp),
                                text = "${transactionTypeToSymbol(transactionType = bankTransaction.smsTransactionType)} ${
                                    if (currency.last()
                                            .toString() == Constants.INDIAN_CURRENCY
                                    ) simplifyAmountIndia(
                                        bankTransaction.amount
                                    ) else simplifyAmount(bankTransaction.amount)
                                } ${currency.last()}",
                                fontFamily = fontInter,
                                fontWeight = FontWeight.Medium,
                                fontSize = TEXT_FIELD_SIZE,
                                color = textColorBW,
                                textAlign = TextAlign.End
                            )

                        }
                    }

                }

            }
        }
    }


}