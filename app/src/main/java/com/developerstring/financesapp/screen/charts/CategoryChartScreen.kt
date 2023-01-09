package com.developerstring.financesapp.screen.charts

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.CustomChip
import com.developerstring.financesapp.ui.components.PieChart
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.categorySortToText
import com.developerstring.financesapp.util.state.CategorySortState
import com.developerstring.financesapp.util.state.RequestState
import com.developerstring.financesapp.util.textToCategorySort
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun CategoryChartScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    profileViewModel: ProfileViewModel
) {

    val categories by profileViewModel.allCategories.collectAsState()

    Sample(
        sharedViewModel = sharedViewModel,
        navController = navController,
        profileViewModel = profileViewModel,
        categoryList = categories
    )


}

@Composable
fun Sample(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    profileViewModel: ProfileViewModel,
    categoryList: RequestState<List<CategoryModel>>
) {


    val currency = profileViewModel.profileCurrency.collectAsState().value.last().toString()

    var data by remember {
        mutableStateOf(listOf<Long>())
    }

    val calendar = Calendar.getInstance()

    val month = calendar.get(Calendar.MONTH) + 1
    val year = calendar.get(Calendar.YEAR)

    if (categoryList is RequestState.Success) {
        MonthCategorySum(
            sharedViewModel = sharedViewModel,
            month = month.toString(),
            year = year.toString(),
            data = { data = it },
            categoryList = categoryList.data
        )
    }



    // creating the Data of Pie Chart which contains Category and Amount spent in that category
    val rawData = mutableMapOf<String, Long>()

    if (categoryList is RequestState.Success) {
        categoryList.data.sortedBy { it.category }.forEachIndexed { index, value ->
            rawData[value.category] = data[index]
        }
    }

    var categorySortEnable by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        categorySortEnable = true
    }

    var categorySortState by remember {
        mutableStateOf(CategorySortState.HIGH_TO_LOW)
    }

    val categorySortList = mutableListOf(
        CategorySortState.HIGH_TO_LOW,
        CategorySortState.LOW_TO_HIGH,
        CategorySortState.BY_NAME,
    )

    var chartData = rawData.toList().sortedBy { (_, value) -> value }.reversed().toMap()

    if (categorySortEnable) {
        chartData = when (categorySortState) {
            CategorySortState.HIGH_TO_LOW -> {
                rawData.toList().sortedBy { (_, value) -> value }.reversed().toMap()
            }
            CategorySortState.LOW_TO_HIGH -> {
                rawData.toList().sortedBy { (_, value) -> value }
                    .sortedBy { (_, value) -> value == 0L }.toMap()
            }
            CategorySortState.BY_NAME -> {
                rawData.toList().sortedBy { (key, _) -> key }.sortedBy { (_, value) -> value == 0L }
                    .toMap().toMap()
            }
        }
    }

    val brushBackground = Brush.horizontalGradient(
        colors = listOf(
            Color.Transparent,
            backgroundColor,
        ),
    )

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

            Row(
                modifier = Modifier
                    .padding(top = 5.dp, start = 20.dp)
                    .fillMaxWidth()
                    .height(TOP_APP_BAR_HEIGHT),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.sort_by),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontInter,
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBW
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                    ) {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 30.dp),
                            mainAxisSpacing = 14.dp,
                            crossAxisSpacing = 14.dp
                        ) {
                            categorySortList.forEach {
                                CustomChip(
                                    title = it.categorySortToText(),
                                    selected = categorySortState.categorySortToText(),
                                    image = Icons.Rounded.Check,
                                    key = false,
                                    onSelected = { selected ->
                                        categorySortState = selected.textToCategorySort()
                                    },
                                    selectedColor = UIBlue,
                                    color = colorDarkGray,
                                    textColor = textColorBW,
                                    selectedTextColor = Color.White,
                                    iconColor = Color.White
                                )
                            }
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(40.dp)
                                .background(brushBackground)
                        )
                    }
                }


            }

            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            ) {


                PieChart(
                    data = chartData,
                    currency = currency
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
    year: String,
    categoryList: List<CategoryModel>
) {

    categoryList.sortedBy { it.category }.forEachIndexed { index, value ->
        sharedViewModel.getCategorySum(
            month = month,
            year = year,
            category = value.category,
            transaction_type = SPENT,
            month_no = index
        )
    }

    val dataArray by sharedViewModel.categorySum.collectAsState()

    data(dataArray)

}