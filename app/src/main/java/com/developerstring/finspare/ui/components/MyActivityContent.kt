package com.developerstring.finspare.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.theme.EXTRA_SMALL_TEXT_SIZE
import com.developerstring.finspare.ui.theme.LightGray
import com.developerstring.finspare.ui.theme.SMALL_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.UIBlue
import com.developerstring.finspare.ui.theme.contentColorLBLD
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.Constants.INDIAN_CURRENCY
import com.developerstring.finspare.util.GetLastWeekTransactions
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.lastWeekDateCalculator
import com.developerstring.finspare.util.monthDateCalculator
import com.developerstring.finspare.util.simplifyAmount
import com.developerstring.finspare.util.simplifyAmountIndia
import com.developerstring.finspare.util.state.RoundTypeBarChart

@Composable
fun MyActivityContent(
    sharedViewModel: SharedViewModel,
    day_: Int,
    month_: Int,
    year_: Int,
    currency: String,
    navController: NavController,
    language: String
) {

    var day = listOf<Int>()
    var month = listOf<Int>()
    var year = listOf<Int>()
    var weekTransactions = listOf<Int>()

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val languageText = LanguageText(language = language)

    lastWeekDateCalculator(
        day = day_,
        month = month_,
        year = year_,
        day_ = {
            day = it.reversed()
        },
        month_ = {
            month = it.reversed()
        },
        year_ = {
            year = it.reversed()
        }
    )
    GetLastWeekTransactions(
        days = day,
        months = month,
        years = year,
        sharedViewModel = sharedViewModel,
        transactions = {
            weekTransactions = it
        }
    )

    Surface(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 10.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    navController.navigate(NavRoute.ActivityChartScreen.route)
                }
            ),
        elevation = 2.dp,
        color = contentColorLBLD,
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = languageText.myActivity),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBW
                )
                Image(
                    modifier = Modifier
                        .rotate(-90f)
                        .size(32.dp),
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "arrow",
                    colorFilter = ColorFilter.tint(textColorBW)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, end = 10.dp)
            ) {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = stringResource(id = languageText.lastWeek),
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = EXTRA_SMALL_TEXT_SIZE,
                        color = textColorBW
                    )

                    WeeklyTransactionChart(
                        data = weekTransactions,
                        dates = day
                    )

                    Column {

                        Text(
                            text = if (currency == INDIAN_CURRENCY) simplifyAmountIndia(
                                weekTransactions.sum()
                            ) else simplifyAmount(weekTransactions.sum()),
                            fontWeight = FontWeight.Medium,
                            fontSize = TEXT_FIELD_SIZE,
                            color = textColorBW
                        )
                        Text(
                            text = stringResource(id = languageText.spentThisWeek),
                            fontWeight = FontWeight.Normal,
                            fontSize = SMALL_TEXT_SIZE,
                            color = textColorBW
                        )

                    }
                }
            }

        }

    }

}

@Composable
fun WeeklyTransactionChart(
    data: List<Int>,
    dates: List<Int>
) {

    val maxValue = data.max()
    val amount = mutableListOf<Float>()

    data.forEach {
        if (it == 0) {
            amount.add(0f)
        } else {
            amount.add(it.toFloat() / maxValue.toFloat())
        }
    }

    Row(modifier = Modifier.fillMaxWidth()) {

        BarChart(
            data = amount,
            date = dates,
            height = 100.dp,
            roundType = RoundTypeBarChart.CIRCULAR_SHAPE,
            barWidth = 17.dp,
            barColor = UIBlue,
            backBarColor = LightGray.copy(alpha = if (Constants.DARK_THEME_ENABLE) 0.5f else 0.2f),
            barArrangement = Arrangement.End,
            alignment = Alignment.End,
            point = dates.last(),
        )

//        Text(text = amount.toString())

    }
}

@Composable
fun MonthTransactions(
    sharedViewModel: SharedViewModel,
    dates: (List<Int>) -> Unit,
    monthData: (List<Int>) -> Unit,
    month: Int,
    year: Int,
    transactionType: String
) {

    var days = listOf<Int>()
    var months = listOf<Int>()
    var years = listOf<Int>()

    monthDateCalculator(
        month = month,
        year = year,
        day_ = {
            days = it
        },
        month_ = {
            months = it
        },
        year_ = {
            years = it
        }
    )

    for (i in 0..days.lastIndex) {
        sharedViewModel.searchMonthPayment(
            day = days[i].toString(),
            month = months[i].toString(),
            year = years[i].toString(),
            transaction_type = transactionType,
            day_no = i
        )
    }

    val monthDataList by sharedViewModel.dayPayment.collectAsState()

    dates(days)
    monthData(monthDataList)

//    Text(text = months.toString())
//    Text(text = years.toString())

}