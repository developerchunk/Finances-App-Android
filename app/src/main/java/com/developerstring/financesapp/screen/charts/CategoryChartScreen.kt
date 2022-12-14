package com.developerstring.financesapp.screen.charts

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.PieChart
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.Constants.SUB_CATEGORY

@Composable
fun CategoryChartScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController
) {

    var data by remember {
        mutableStateOf(listOf<Long>())
    }

    val calendar = Calendar.getInstance()

    val month = calendar.get(Calendar.MONTH)+1
    val year = calendar.get(Calendar.YEAR)

    MonthCategorySum(sharedViewModel = sharedViewModel, month = month.toString(), year = year.toString(), data = { data = it })

    // creating the Data of Pie Chart which contains Category and Amount spent in that category
    val rawData = mutableMapOf<String, Long>()
    SUB_CATEGORY.keys.sorted().forEachIndexed { index, value ->
        rawData[value] = data[index]
    }

    val chartData = rawData.toList().sortedBy { (_, value) -> value }.reversed().toMap()

    Scaffold(topBar = {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = TOP_APP_BAR_ELEVATION,
            color = backgroundColorBW
        ) {

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
                    text = stringResource(id = R.string.categories_chart),
                    fontFamily = fontOpenSans,
                    fontWeight = FontWeight.Medium,
                    fontSize = LARGE_TEXT_SIZE,
                    color = textColorBW,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }


        }
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .verticalScroll(rememberScrollState())
        ) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
            ) {


                PieChart(
                    data = chartData
                )

            }

        }

    }


}

@Composable
fun MonthCategorySum(
    sharedViewModel: SharedViewModel,
    data: (List<Long>) -> Unit,
    month: String,
    year: String
) {

    SUB_CATEGORY.keys.sorted().forEachIndexed { index, value ->
        sharedViewModel.getCategorySum(
            month = month,
            year = year,
            category = value,
            transaction_type = SPENT,
            month_no = index
        )
    }

    val dataArray by sharedViewModel.categorySum.collectAsState()

    data(dataArray)

}