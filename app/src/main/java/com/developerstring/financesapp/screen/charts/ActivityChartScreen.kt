package com.developerstring.financesapp.screen.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.screen.navscreens.content.homescreen.MonthTransactions
import com.developerstring.financesapp.sharedviewmodel.PublicSharedViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.LineChart
import com.developerstring.financesapp.ui.components.SimpleChipButton
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.ADD_TRANSACTION_TYPE
import com.developerstring.financesapp.util.Constants.CURRENCY
import com.developerstring.financesapp.util.Constants.DARK_THEME_ENABLE
import com.developerstring.financesapp.util.Constants.INDIAN_CURRENCY
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.dataclass.LineChartData
import com.developerstring.financesapp.util.keyToTransactionType
import com.developerstring.financesapp.util.monthToName
import com.developerstring.financesapp.util.simplifyAmount
import com.developerstring.financesapp.util.simplifyAmountIndia
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun ActivityChartScreen(
    sharedViewModel: SharedViewModel,
    publicSharedViewModel: PublicSharedViewModel,
    navController: NavController
) {

    var monthPayment by remember {
        mutableStateOf(listOf<Int>())
    }
    var dates by remember {
        mutableStateOf(listOf<Int>())
    }
    val data = mutableStateListOf<LineChartData>()

//    val data_ = mutableStateListOf<LineChartData>()

    var month by remember {
        mutableStateOf(11)
    }
    var year by remember {
        mutableStateOf(2022)
    }
    var selectedTransactionType by remember {
        mutableStateOf(SPENT)
    }
    var sel by remember {
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

    LaunchedEffect(key1 = selectedTransactionType,month) {
        sel = true
    }

    dates.forEachIndexed { index, value ->
        data.add(index = index, element = LineChartData(date = value, amount = monthPayment[index].toDouble()))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT),
            elevation = TOP_APP_BAR_ELEVATION,
            color = backgroundColorBW
        ) {

            Row(
                modifier = Modifier.fillMaxSize().padding(start = 20.dp),
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
                    text = "My Activity",
                    fontFamily = fontOpenSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = LARGE_TEXT_SIZE,
                    color = textColorBW
                )
            }
        }

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

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${monthToName(month)} $year",
                    fontSize = TEXT_FIELD_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    color = textColorBW
                )
                Text(
                    text = "${selectedTransactionType.keyToTransactionType()} ${
                        if (CURRENCY == INDIAN_CURRENCY) simplifyAmountIndia(monthPayment.sum())
                        else simplifyAmount(monthPayment.sum())
                    }$CURRENCY",
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

        Column(
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp, top = 40.dp, bottom = 10.dp)
                .fillMaxWidth()
            ,
        ) {
            LineChart(
                infos = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                textColor = textColorBW.hashCode(),
                graphColor = if (DARK_THEME_ENABLE) LightGreen else UIBlue
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

