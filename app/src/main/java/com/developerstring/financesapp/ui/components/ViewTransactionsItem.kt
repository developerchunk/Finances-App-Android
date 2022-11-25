package com.developerstring.financesapp.ui.components

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.simplifyAmount
import com.developerstring.financesapp.util.simplifyAmountIndia
import com.developerstring.financesapp.util.transactionTypeToSymbol

@Composable
fun TransactionsItemView(
    transactionModel: TransactionModel,
    currency: String,
    navigateToDetails: (id: Int) -> Unit
) {

//    val sameDate = TRANS == transactionModel.date
    val sameDate by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val extraInfoStatus = transactionModel.info != ""

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
                    navigateToDetails(transactionModel.id)
                }),
        color = Color.Transparent,
    ) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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


            }

            Surface(
                elevation = 5.dp,
                modifier = Modifier.padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                color = contentColorCard
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

                        val (text,text1, text2, text3) = createRefs()

                        Text(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .fillMaxWidth(0.5f)
                                .constrainAs(text) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                },
                            text = transactionModel.category,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            fontSize = SMALLEST_TEXT_SIZE,
                            color = colorGray,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )

                        Text(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .fillMaxWidth(0.5f)
                                .constrainAs(text1) {
                                    start.linkTo(parent.start)
                                    top.linkTo(text.bottom)
                                },
                            text = transactionModel.subCategory,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            fontSize = TEXT_FIELD_SIZE,
                            color = textColorBW,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
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
                                if (currency.last().toString() == INDIAN_CURRENCY) simplifyAmountIndia(
                                    transactionModel.amount
                                ) else simplifyAmount(transactionModel.amount)
                            } ${currency.last()}",
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            fontSize = TEXT_FIELD_SIZE,
                            color = textColorBW,
                        )

                    }

                }
            }

        }

    }


}