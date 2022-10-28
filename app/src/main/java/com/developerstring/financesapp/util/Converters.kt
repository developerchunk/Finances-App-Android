package com.developerstring.financesapp.util

import com.developerstring.financesapp.util.Constants.ADD_FUND
import com.developerstring.financesapp.util.Constants.SAVINGS
import com.developerstring.financesapp.util.Constants.SPENT
import java.text.DecimalFormat
import kotlin.math.abs

fun keyToTransactionType(key: String): String {
    return when (key) {
        SPENT -> "Spent"
        ADD_FUND -> "Add Fund"
        SAVINGS -> "Savings"
        else -> ""
    }
}

fun transactionTypeToSymbol(transactionType: String): String {
    return when (transactionType) {
        ADD_FUND -> "+"
        SPENT -> "-"
        SAVINGS -> ""
        else -> "-/+"
    }
}

fun simplifyAmount(amount: Int): String {
    val am: Float

    val df = DecimalFormat("##.##")

    var simplify = ""

    if (abs(amount / 1000000) >= 1) {
        am = amount.toFloat() / 1000000
        simplify = df.format(am) + "M"
    } else if (abs(amount / 1000) >= 1) {
        am = amount.toFloat() / 1000
        simplify = df.format(am) + "K"
    } else {
        simplify = amount.toString()
    }

    return simplify
}

fun simplifyAmountIndia(amount: Int): String {
    val am: Float

    val df = DecimalFormat("##.##")

    var simplify = ""

    if (abs(amount / 10000000) >= 1) {
        am = amount.toFloat() / 10000000
        simplify = df.format(am) + "Cr"
    } else if (abs(amount / 100000) >= 1) {
        am = amount.toFloat() / 100000
        simplify = df.format(am) + "L"
    } else if (abs(amount / 1000) >= 1) {
        am = amount.toFloat() / 1000
        simplify = df.format(am) + "K"
    } else {
        simplify = amount.toString()
    }

    return simplify
}

fun lastWeekDateCalculator(
    day: Int,
    month: Int,
    year: Int,
    day_: (List<Int>) -> Unit,
    month_: (List<Int>) -> Unit,
    year_: (List<Int>) -> Unit,
) {

    var days = day + 1
    var months = month
    var years = year

    val dates = arrayListOf<Int>()
    val mMonth = arrayListOf<Int>()
    val mYear = arrayListOf<Int>()

    for (i in 1..7) {

        if (days != 0) {
            days -= 1
            dates.add(days)
            mMonth.add(months)
            mYear.add(years)
        } else {
            months -= 1
            if (months == 0) {
                years -= 1
            }
            mMonth.add(
                if (months == 0) {
                    months = 12; 12
                } else months
            )
            mYear.add(years)
            days = monthLastDate(
                month = months,
                year = years
            ) + 1
            dates.add(days)
        }
    }

//    dates.removeLast()
//    mMonth.removeLast()
//    mYear.removeLast()

    day_(dates)
    month_(mMonth)
    year_(mYear)

}
fun monthDateCalculator(
    month: Int,
    year: Int,
    day_: (List<Int>) -> Unit,
    month_: (List<Int>) -> Unit,
    year_: (List<Int>) -> Unit,
) {

    val dates = arrayListOf<Int>()
    val mMonth = arrayListOf<Int>()
    val mYear = arrayListOf<Int>()

    val monthLastDate = monthLastDate(month = month, year = year)

    for (i in 1..monthLastDate) {
        dates.add(i)
        mMonth.add(month)
        mYear.add(year)
    }

    day_(dates)
    month_(mMonth)
    year_(mYear)

}

fun monthLastDate(
    month: Int,
    year: Int
): Int {
    return when (month) {
        1 -> 31
        2 ->
            if (year % 4 == 0) {
                if (year % 100 == 0 && year % 400 != 0) {
                    28
                } else 29
            } else {
                28
            }
        3 -> 31
        4 -> 30
        5 -> 31
        6 -> 30
        7 -> 31
        8 -> 31
        9 -> 30
        10 -> 31
        11 -> 30
        12 -> 31
        else -> 31
    }
}
