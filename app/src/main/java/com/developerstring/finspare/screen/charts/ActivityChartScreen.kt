package com.developerstring.finspare.screen.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.components.ActivityBarChart
import com.developerstring.finspare.ui.components.LineChart
import com.developerstring.finspare.ui.components.MonthTransactions
import com.developerstring.finspare.ui.components.MonthYearPickerCalender
import com.developerstring.finspare.ui.components.SimpleChipButton
import com.developerstring.finspare.ui.components.TabLayoutChartScreen
import com.developerstring.finspare.ui.theme.LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.LightGreen
import com.developerstring.finspare.ui.theme.MEDIUM_TEXT_SIZE
import com.developerstring.finspare.ui.theme.SMALL_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_ELEVATION
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.UIBlue
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.backgroundColorBW
import com.developerstring.finspare.ui.theme.colorGray
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants.ADD_TRANSACTION_TYPE
import com.developerstring.finspare.util.Constants.DARK_THEME_ENABLE
import com.developerstring.finspare.util.Constants.INDIAN_CURRENCY
import com.developerstring.finspare.util.Constants.SPENT
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.dataclass.LineChartData
import com.developerstring.finspare.util.keyToTransactionType
import com.developerstring.finspare.util.monthToName
import com.developerstring.finspare.util.simplifyAmount
import com.developerstring.finspare.util.simplifyAmountIndia
import com.developerstring.finspare.util.state.RoundTypeBarChart
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import java.util.Calendar

@Composable
fun ActivityChartScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController
) {


    val calender = Calendar.getInstance()
    val month = calender.get(Calendar.MONTH) + 1
    val year = calender.get(Calendar.YEAR)

    val language by profileViewModel.profileLanguage.collectAsState()
    val languageText = LanguageText(language)

    val monthChart = languageText.month
    val quarterChart = languageText.quarter

    val chartType by remember {
        mutableStateOf(listOf(monthChart, quarterChart))
    }
    var chartTypeSelected by remember {
        mutableIntStateOf(monthChart)
    }

    val currency = profileViewModel.profileCurrency.collectAsState().value.last().toString()


    Scaffold(topBar = {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = TOP_APP_BAR_ELEVATION,
            color = backgroundColorBW
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TOP_APP_BAR_HEIGHT)
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            modifier = Modifier
                                .width(28.dp)
                                .height(24.dp),
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "back_arrow",
                            tint = textColorBW
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = stringResource(id = languageText.activityChartText),
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = LARGE_TEXT_SIZE,
                        color = textColorBW,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }

                FlowRow(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    mainAxisAlignment = MainAxisAlignment.SpaceEvenly,
                    crossAxisAlignment = FlowCrossAxisAlignment.Center,
                ) {

                    chartType.forEach {
                        TabLayoutChartScreen(
                            text = it,
                            select = chartTypeSelected,
                            onClick = { key ->
                                chartTypeSelected = key
                            }
                        )
                    }

                }
            }

        }
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {

            when (chartTypeSelected) {
                monthChart -> {
                    MonthActivityChart(
                        sharedViewModel = sharedViewModel,
                        month_ = month,
                        year_ = year,
                        currency = currency
                    )
                }
                quarterChart -> {
                    QuarterActivityChart(
                        sharedViewModel = sharedViewModel,
                        month_ = calender.get(Calendar.MONTH) + 1,
                        year_ = calender.get(Calendar.YEAR),
                        currency = currency
                    )
                }
            }

        }
    }


}

@Composable
fun MonthActivityChart(
    sharedViewModel: SharedViewModel,
    month_: Int,
    year_: Int,
    currency: String
) {

    var monthPayment by remember {
        mutableStateOf(listOf<Int>())
    }
    var dates by remember {
        mutableStateOf(listOf<Int>())
    }
    val data = mutableListOf<LineChartData>()

//    val data_ = mutableStateListOf<LineChartData>()

    var month by remember {
        mutableStateOf(month_)
    }
    var year by remember {
        mutableStateOf(year_)
    }
    var selectedTransactionType by remember {
        mutableStateOf(SPENT)
    }
    var sel by remember {
        mutableStateOf(false)
    }

    var swipeEnable by remember {
        mutableStateOf(true)
    }

    var monthPickerClicked by remember {
        mutableStateOf(false)
    }

    MonthTransactions(
        sharedViewModel = sharedViewModel,
        dates = {
            dates = it
        },
        monthData = {
            monthPayment = it
        },
        month = month,
        year = year,
        transactionType = selectedTransactionType
    )

    if (sel) {
        MonthTransactions(
            sharedViewModel = sharedViewModel,
            dates = {
                dates = it
            },
            monthData = {
                monthPayment = it
            },
            month = month,
            year = year,
            transactionType = selectedTransactionType
        )
        sel = false
    }

    LaunchedEffect(key1 = true) {
        swipeEnable = true
    }

    LaunchedEffect(key1 = selectedTransactionType, month) {
        sel = true
    }

    dates.forEachIndexed { index, value ->
        data.add(
            index = index,
            element = LineChartData(date = value, amount = monthPayment[index].toDouble())
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                if (month == 1) {
                    month = 12
                    year--
                } else month--
            }) {
                Icon(
                    modifier = Modifier
                        .rotate(90f)
                        .size(28.dp),
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "arrow",
                    tint = colorGray
                )
            }

            Column(
                modifier = Modifier.clickable {
                    monthPickerClicked = true
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${monthToName(month)} $year",
                    fontSize = TEXT_FIELD_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    color = textColorBW
                )
                Text(
                    text = "${selectedTransactionType.keyToTransactionType()} ${
                        if (currency == INDIAN_CURRENCY) simplifyAmountIndia(monthPayment.sum())
                        else simplifyAmount(monthPayment.sum())
                    }$currency",
                    fontSize = SMALL_TEXT_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    color = textColorBW
                )
            }

            IconButton(onClick = {
                if (month == 12) {
                    month = 1
                    year++
                } else month++
            }) {
                Icon(
                    modifier = Modifier
                        .rotate(-90f)
                        .size(28.dp),
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "arrow",
                    tint = colorGray
                )
            }

        }

        if (monthPickerClicked) {
            MonthYearPickerCalender(
                visible = true,
                month_ = month,
                year_ = year,
                confirmClicked = { m, y ->
                    monthPickerClicked = false
                    month = m
                    year = y
                },
                cancelClicked = {
                    monthPickerClicked = false
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp, top = 40.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            LineChart(
                chartInfo = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                textColor = textColorBW.hashCode(),
                graphColor = if (DARK_THEME_ENABLE) LightGreen else UIBlue,
                currency = currency
            )
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 16.dp,
            mainAxisAlignment = MainAxisAlignment.Center,
            crossAxisAlignment = FlowCrossAxisAlignment.Center
        ) {
            ADD_TRANSACTION_TYPE.forEach {

                SimpleChipButton(
                    text = it,
                    select = selectedTransactionType,
                    onClick = { key ->
                        selectedTransactionType = key
                    }
                )

            }
        }

    }

}

@Composable
fun QuarterActivityChart(
    sharedViewModel: SharedViewModel,
    month_: Int,
    year_: Int,
    currency: String
) {

    var month by remember {
        mutableStateOf(month_)
    }
    var month2 by remember {
        mutableStateOf(month_ + 1)
    }
    var month3 by remember {
        mutableStateOf(month2 + 1)
    }
    var year by remember {
        mutableStateOf(year_)
    }
    var year2 by remember {
        mutableStateOf(year_)
    }
    var year3 by remember {
        mutableStateOf(year_)
    }
    var transactionType by remember {
        mutableStateOf(SPENT)
    }

    var monthData1 by remember {
        mutableStateOf(0)
    }
    var monthData2 by remember {
        mutableStateOf(0)
    }
    var monthData3 by remember {
        mutableStateOf(0)
    }

    var sel by remember {
        mutableStateOf(false)
    }

//    var monthPickerClicked by remember {
//        mutableStateOf(false)
//    }

    when (month2) {
        13 -> {
            month2 = 1
            year2++
        }
        0 -> {
            month2 = 12
            year2--
        }
    }

    when (month3) {
        13 -> {
            month3 = 1
            year3++
        }
        14 -> {
            month3 = 2
            year3++
        }
        0 -> {
            month3 = 12
            year3--
        }
    }

    QuarterRequestData(
        sharedViewModel = sharedViewModel,
        month = month,
        month2 = month2,
        month3 = month3,
        year = year,
        year2 = year2,
        year3 = year3,
        transactionType = transactionType,
        monthList1 = {
            monthData1 = it
        },
        monthList2 = {
            monthData2 = it
        },
        monthList3 = {
            monthData3 = it
        }
    )

    if (sel) {
        QuarterRequestData(
            sharedViewModel = sharedViewModel,
            month = month,
            month2 = month2,
            month3 = month3,
            year = year,
            year2 = year2,
            year3 = year3,
            transactionType = transactionType,
            monthList1 = {
                monthData1 = it
            },
            monthList2 = {
                monthData2 = it
            },
            monthList3 = {
                monthData3 = it
            }
        )
    }

    LaunchedEffect(key1 = month, transactionType, year) {
        sel = true
    }

    val quarterList = mutableListOf(monthData1, monthData2, monthData3)

    val amount = mutableListOf<Float>()
    quarterList.forEachIndexed { index, it ->
        if (it == 0) {
            amount.add(index = index, 0f)
        } else {
            amount.add(index = index, it.toFloat() / quarterList.max().toFloat())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                if (month == 1) {
                    month = 12
                    year--
                } else month--
                if (month2 == 1) {
                    month2 = 12
                    year2--
                } else month2--
                if (month3 == 1) {
                    month3 = 12
                    year3--
                } else month3--
            }) {
                Icon(
                    modifier = Modifier
                        .rotate(90f)
                        .size(28.dp),
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "arrow",
                    tint = colorGray
                )
            }

            Column(
//                modifier = Modifier
//                    .clickable {
//                    monthPickerClicked = true
//                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${monthToName(month)} to ${monthToName(month3)} $year3",
                    fontSize = MEDIUM_TEXT_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    color = textColorBW
                )
                Text(
                    text = "Total ${transactionType.keyToTransactionType()} ${
                        if (currency == INDIAN_CURRENCY) simplifyAmountIndia(quarterList.sum())
                        else simplifyAmount(quarterList.sum())
                    }$currency",
                    fontSize = SMALL_TEXT_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    color = textColorBW
                )
            }

            IconButton(onClick = {
                if (month == 12) {
                    month = 1
                    year++
                } else month++
                if (month2 == 12) {
                    month2 = 1
                    year2++
                } else month2++
                if (month3 == 12) {
                    month3 = 1
                    year3++
                } else month3++
            }) {
                Icon(
                    modifier = Modifier
                        .rotate(-90f)
                        .size(28.dp),
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "arrow",
                    tint = colorGray
                )
            }

        }

//        if (monthPickerClicked) {
//            MonthYearPickerCalender(
//                visible = true,
//                month_ = month,
//                year_ = year,
//                confirmClicked = { m,y ->
//                    monthPickerClicked = false
//                    month = m
//                    year = 2022
//                },
//                cancelClicked = {
//                    monthPickerClicked = false
//                }
//            )
//        }

        Column(
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp, top = 40.dp, bottom = 10.dp)
                .fillMaxWidth(),
        ) {

            ActivityBarChart(
                graphBarData = amount,
                xAxisScaleData = listOf(month, month2, month3),
                amount = quarterList,
                height = 300.dp,
                roundType = RoundTypeBarChart.TOP_CURVE,
                barWidth = 18.dp,
                barColor = if (DARK_THEME_ENABLE) LightGreen else UIBlue,
                backBarColor = Color.Transparent,
                barArrangement = Arrangement.SpaceEvenly,
                point = month_,
                point_size = 7.dp,
                currency = currency
            )
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 16.dp,
            mainAxisAlignment = MainAxisAlignment.Center,
            crossAxisAlignment = FlowCrossAxisAlignment.Center
        ) {
            ADD_TRANSACTION_TYPE.forEach {

                SimpleChipButton(
                    text = it,
                    select = transactionType,
                    onClick = { key ->
                        transactionType = key
                    }
                )

            }
        }

    }

}

@Composable
fun QuarterRequestData(
    sharedViewModel: SharedViewModel,
    month: Int,
    month2: Int,
    month3: Int,
    year: Int,
    year2: Int,
    year3: Int,
    transactionType: String,
    monthList1: (Int) -> Unit,
    monthList2: (Int) -> Unit,
    monthList3: (Int) -> Unit,
) {

    // month1 -> month2 -> month3 is the sequence
    sharedViewModel.getQuarterMonth1(
        month = month.toString(),
        year = year.toString(),
        transaction_type = transactionType
    )
    sharedViewModel.getQuarterMonth2(
        month = (month2).toString(),
        year = year2.toString(),
        transaction_type = transactionType
    )
    sharedViewModel.getQuarterMonth3(
        month = (month3).toString(),
        year = year3.toString(),
        transaction_type = transactionType
    )

    val quarterMonth1 by sharedViewModel.quarterMonth1.collectAsState()
    val quarterMonth2 by sharedViewModel.quarterMonth2.collectAsState()
    val quarterMonth3 by sharedViewModel.quarterMonth3.collectAsState()

    monthList1(quarterMonth1.toInt())
    monthList2(quarterMonth2.toInt())
    monthList3(quarterMonth3.toInt())


}

