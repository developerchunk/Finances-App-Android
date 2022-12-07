package com.developerstring.financesapp.screen.charts

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.Constants.SUB_CATEGORY

@Composable
fun CategoryChartScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController
) {

    val context = LocalContext.current

    var data by remember {
        mutableStateOf(listOf<Long>())
    }

    MonthCategorySum(sharedViewModel = sharedViewModel, data = {

        data = it

    })

    Column {
        Text(text = data.toString())

        SUB_CATEGORY.keys.forEach {
            Text(text = it)
        }

    }


}

@Composable
fun MonthCategorySum(
    sharedViewModel: SharedViewModel,
    data: (List<Long>) -> Unit
) {

    SUB_CATEGORY.keys.forEachIndexed { index, value ->
        sharedViewModel.getCategorySum(
            month = "12",
            year = "2022",
            category = value,
            transaction_type = SPENT,
            month_no = index
        )
    }

    val dataArray by sharedViewModel.categorySum.collectAsState()

    data(dataArray)

}