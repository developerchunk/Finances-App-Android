package com.developerstring.financesapp.screen.charts

import android.icu.util.Calendar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.developerstring.financesapp.ui.components.SimpleChipButton
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.*
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.state.CategorySortState
import com.developerstring.financesapp.util.state.RequestState
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun CategoryChartScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    profileViewModel: ProfileViewModel
) {

    val categories by profileViewModel.allCategories.collectAsState()
    val currency = profileViewModel.profileCurrency.collectAsState().value.last().toString()

    val calendar = Calendar.getInstance()

    val month by remember {
        mutableStateOf(calendar.get(Calendar.MONTH) + 1)
    }
    val year by remember {
        mutableStateOf(calendar.get(Calendar.YEAR))
    }
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = if (menuExpanded) 0.dp else TOP_APP_BAR_ELEVATION,
            color = backgroundColorBW
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TOP_APP_BAR_HEIGHT)
                    .padding(start = 10.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                    text = stringResource(id = R.string.categories_chart),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = LARGE_TEXT_SIZE,
                    color = textColorBW,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                IconButton(onClick = {
                    menuExpanded = !menuExpanded
                }) {
                    Icon(
                        modifier = Modifier
                            .width(20.dp)
                            .height(24.dp),
                        painter = painterResource(id = R.drawable.menu_expand),
                        contentDescription = "back_arrow",
                        tint = textColorBW
                    )
                }
            }

        }
    }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .verticalScroll(rememberScrollState())
        ) {


            Sample(
                sharedViewModel = sharedViewModel,
                categoryList = categories,
                month_ = month,
                year_ = year,
                currency = currency,
                menuExpanded = menuExpanded
            )

        }
    }
}

@Composable
fun Sample(
    sharedViewModel: SharedViewModel,
    categoryList: RequestState<List<CategoryModel>>,
    month_: Int,
    year_: Int,
    currency: String,
    menuExpanded: Boolean
) {

    var data by remember {
        mutableStateOf(listOf<Long>())
    }

    var sel by remember {
        mutableStateOf(false)
    }

    var month by remember {
        mutableStateOf(month_)
    }

    var year by remember {
        mutableStateOf(year_)
    }

    var selectedTransactionType by remember {
        mutableStateOf(SPENT)
    }

    if (categoryList is RequestState.Success) {
        MonthCategorySum(
            sharedViewModel = sharedViewModel,
            month = month.toString(),
            year = year.toString(),
            data = { data = it },
            categoryList = categoryList.data,
            transactionType = selectedTransactionType
        )
    }

    if (sel) {
        if (categoryList is RequestState.Success) {
            MonthCategorySum(
                sharedViewModel = sharedViewModel,
                month = month.toString(),
                year = year.toString(),
                data = { data = it },
                categoryList = categoryList.data,
                transactionType = selectedTransactionType
            )
        }
        sel = false
    }

    LaunchedEffect(key1 = selectedTransactionType, month, menuExpanded) {
        sel = true
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



    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AnimatedVisibility(visible = menuExpanded) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = TOP_APP_BAR_ELEVATION,
                color = backgroundColorBW
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp),
                ) {

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)) {
                        Text(
                            text = stringResource(id = R.string.filter),
                            fontFamily = fontInter,
                            fontSize = MEDIUM_TEXT_SIZE,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW
                        )

                        Icon(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(22.dp),
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = stringResource(
                                id = R.string.filter
                            ),
                            tint = textColorBW
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
                        Constants.ADD_TRANSACTION_TYPE.forEach {

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
        }

        Column(modifier = Modifier.fillMaxWidth()) {

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
                        text =
                        "${selectedTransactionType.keyToTransactionType()} ${
                            if (currency == Constants.INDIAN_CURRENCY) simplifyAmountIndia(
                                data.sum().toInt()
                            )
                            else simplifyAmount(data.sum().toInt())
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

        }

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

@Composable
fun MonthCategorySum(
    sharedViewModel: SharedViewModel,
    data: (List<Long>) -> Unit,
    month: String,
    year: String,
    categoryList: List<CategoryModel>,
    transactionType: String
) {

    categoryList.sortedBy { it.category }.forEachIndexed { index, value ->
        sharedViewModel.getCategorySum(
            month = month,
            year = year,
            category = value.category,
            transaction_type = transactionType,
            month_no = index
        )
    }

    val dataArray by sharedViewModel.categorySum.collectAsState()

    data(dataArray)

}