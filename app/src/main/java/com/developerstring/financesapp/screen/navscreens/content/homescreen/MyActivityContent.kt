package com.developerstring.financesapp.screen.navscreens.content.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.developerstring.financesapp.util.Constants
import com.developerstring.financesapp.util.lastWeekDateCalculator
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun MyActivityContent(
    sharedViewModel: SharedViewModel,
    day_: Int,
    month_: Int,
    year_: Int
) {

    var day = listOf<Int>()
    var month = listOf<Int>()
    var year = listOf<Int>()

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



    sharedViewModel.searchDayPayment(
        day = day_.toString(),
        month = month_.toString(),
        year = year_.toString(),
        transaction_type = Constants.SPENT
    )
    val day1List by sharedViewModel.dayPayment.collectAsState()

    val day1Data = day1List.sum()

    Surface(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        color = MaterialTheme.colors.contentColorLBLD,
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
                    fontSize = TEXT_FIELD_SIZE
                )
                Image(
                    modifier = Modifier
                        .rotate(-90f)
                        .size(32.dp),
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "arrow",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.textColorBW)
                )
            }

            Text(
                modifier = Modifier.padding(start = 20.dp, top = 5.dp),
                text = stringResource(id = R.string.last_week),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_SMALL_TEXT_SIZE
            )

            Text(text = day1Data.toString())
            Text(text = day.toString())
            Text(text = month.toString())
            Text(text = year.toString())

        }

    }

}

@Composable
fun WeeklyTransactionChart(
    data: Map<Float, Int>
) {
    Column(modifier = Modifier.fillMaxWidth()) {

    }
}