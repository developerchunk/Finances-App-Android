package com.developerstring.financesapp.screen.navscreens

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.developerstring.financesapp.screen.navscreens.content.homescreen.MonthTransactions
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

@Composable
fun ActivityScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
) {

    var monthPayment by remember {
        mutableStateOf(listOf<Int>())
    }
    var dates by remember {
        mutableStateOf(listOf<Int>())
    }

    MonthTransactions(
       sharedViewModel = sharedViewModel,
        dates = {
            dates = it
        },
        monthData = {
            monthPayment = it
        }
    )

    ActivityScreenDates(
        dates = dates,
        data = monthPayment
    )

}

@Composable
fun ActivityScreenDates(
    data: List<Int>,
    dates: List<Int>
) {

    Text(text = dates.toString())

}