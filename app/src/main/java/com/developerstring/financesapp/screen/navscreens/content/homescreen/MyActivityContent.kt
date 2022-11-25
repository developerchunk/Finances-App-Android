package com.developerstring.financesapp.screen.navscreens.content.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.developerstring.financesapp.R
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.ui.components.BarChart
import com.developerstring.financesapp.util.*
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.Constants.SPENT
import java.util.Calendar

@Composable
fun MyActivityContent(
    sharedViewModel: SharedViewModel,
    day_: Int,
    month_: Int,
    year_: Int,
    currency: String
) {

    var day = listOf<Int>()
    var month = listOf<Int>()
    var year = listOf<Int>()
    var weekTransactions = listOf<Int>()

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
            .fillMaxWidth(),
        elevation = 4.dp,
        color = contentColorLBLD,
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_activity),
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
                        text = stringResource(id = R.string.last_week),
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
                            text = stringResource(id = R.string.spent_this_week),
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
            height = 100.dp
        )

//        Text(text = amount.toString())

    }
}

@Composable
fun MonthTransactions(
    sharedViewModel: SharedViewModel,
    dates: (List<Int>) -> Unit,
    monthData: (List<Int>) -> Unit,
) {

    var days = listOf<Int>()
    var months = listOf<Int>()
    var years = listOf<Int>()

    val calender = Calendar.getInstance()

    monthDateCalculator(
        month = calender.get(Calendar.MONTH)+1,
        year = calender.get(Calendar.YEAR),
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
            transaction_type = SPENT,
            day_no = i
        )
    }

    val monthDataList by sharedViewModel.dayPayment.collectAsState()

    dates(days)
    monthData(monthDataList)

//    Text(text = months.toString())
//    Text(text = years.toString())

}