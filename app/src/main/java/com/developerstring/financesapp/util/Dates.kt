package com.developerstring.financesapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

@Composable
fun GetLastWeekTransactions(
    days: List<Int>,
    months: List<Int>,
    years: List<Int>,
    sharedViewModel: SharedViewModel,
    transactions: (List<Int>) -> Unit
) {

    for (i in 0..6) {
        sharedViewModel.searchDayPayment(
            day = days[i].toString(),
            month = months[i].toString(),
            year = years[i].toString(),
            transaction_type = Constants.SPENT,
            day_no = i
        )
    }
    val day1List by sharedViewModel.dayPayment1.collectAsState()
    val day1 = day1List.sum()
    // day2
    val day2List by sharedViewModel.dayPayment2.collectAsState()
    val day2 = day2List.sum()
    // day3
    val day3List by sharedViewModel.dayPayment3.collectAsState()
    val day3 = day3List.sum()
    // day4
    val day4List by sharedViewModel.dayPayment4.collectAsState()
    val day4 = day4List.sum()
    // day5
    val day5List by sharedViewModel.dayPayment5.collectAsState()
    val day5 = day5List.sum()
    // day6
    val day6List by sharedViewModel.dayPayment6.collectAsState()
    val day6 = day6List.sum()
    // day7
    val day7List by sharedViewModel.dayPayment7.collectAsState()
    val day7 = day7List.sum()

    transactions(listOf(day1,day2,day3,day4,day5,day6,day7))

}
