package com.developerstring.financesapp.util

import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import com.developerstring.financesapp.util.Constants.ACTIVITY_ROUTE
import com.developerstring.financesapp.util.Constants.ADD_FUND
import com.developerstring.financesapp.util.Constants.HOME_ROUTE
import com.developerstring.financesapp.util.Constants.LATEST_FIRST
import com.developerstring.financesapp.util.Constants.OLD_FIRST
import com.developerstring.financesapp.util.Constants.PROFILE_ROUTE
import com.developerstring.financesapp.util.Constants.SAVINGS
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.Constants.THIS_MONTH
import com.developerstring.financesapp.util.Constants.oldFirstFilter
import com.developerstring.financesapp.util.state.CategorySortState
import com.developerstring.financesapp.util.state.RequestState
import java.text.DecimalFormat
import java.util.*
import kotlin.math.abs

fun String.keyToTransactionType(): String {
    return when (this) {
        SPENT -> "Spent"
        ADD_FUND -> "Add Amount"
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

    if (abs(amount / 1000000000) >= 1) {
        am = amount.toFloat() / 1000000000
        simplify = df.format(am) + "B"
    } else if (abs(amount / 1000000) >= 1) {
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

        if (days != 1) {
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
            )
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

fun String.filterListText(): String {

    val calender = Calendar.getInstance()

    oldFirstFilter.value = this == OLD_FIRST

    val month = calender.get(Calendar.MONTH)+1

    return when (this) {
        THIS_MONTH -> "${calender.get(Calendar.YEAR)}${if (month<10) 0 else ""}$month"
        LATEST_FIRST -> ""
        OLD_FIRST -> ""
        else -> this
    }
}

fun Short.addZeroToStart(): String {

    return if (this <= 9) {
        "0$this"
    } else {
        this.toString()
    }

}

class MessageBarContentLastTransaction {

    fun title(
        transactionType: String,
        currency: String,
        amount: Int
    ): String {

        return "Delete ${
            transactionType.keyToTransactionType().uppercase()
        } ${
            if (currency.last().toString() == Constants.INDIAN_CURRENCY)
                simplifyAmountIndia(amount)
            else simplifyAmount(
                amount
            )
        }${currency.last()}"

    }

    fun message(
        category: String,
        subCategory: String,
        day: Short,
        month: Short,
        year: Short,
    ): String {

        return "Are you sure you want to delete \n\n" +
                "Category: $category\n" +
                "Sub Category: $subCategory\n" +
                "Date: $day/$month/$year\n"

    }

}

fun Map<String, List<String>>.mapListToList(): List<String> {

    val list = mutableListOf<String>()

    this.values.forEach {
        it.forEach { str ->
            list.add(str)
        }
    }

    return list.sorted()

}

fun monthToName(
    month: Int
): String {

    return when (month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> {
            "January"
        }
    }

}

fun CategorySortState.categorySortToText(): String {
    return when (this) {
        CategorySortState.HIGH_TO_LOW -> "High To Low"
        CategorySortState.LOW_TO_HIGH -> "Low To High"
        CategorySortState.BY_NAME -> "By Name"
    }
}

fun String.textToCategorySort(): CategorySortState {
    return when (this) {
        "High To Low" -> CategorySortState.HIGH_TO_LOW
        "Low To High" -> CategorySortState.LOW_TO_HIGH
        "By Name" -> CategorySortState.BY_NAME
        else -> CategorySortState.HIGH_TO_LOW
    }
}

fun String.convertStringToAlphabets(length: Int = 35): String {
    return this.filterNot { it == '<' || it == '>' || it == '/' || it == '#' || it == '\'' || it == '"' || it == '\\' || it == '{' || it == '}' || it == '[' || it == ']' || it == '!' || it == '%' || it == '$' || it == '?' || it == '.' }
        .take(length)
}

fun String.convertStringToInt(): String {
    return this.filter { it in '0'..'9' }.take(10)
}

fun String.bottomNavText(
    language: String
): Int {

    val languageText = LanguageText(language = language)

    return when (this) {
        HOME_ROUTE -> languageText.homeBottomNav
        ACTIVITY_ROUTE -> languageText.activityBottomNav
        PROFILE_ROUTE -> languageText.profileBottomNav
        else -> languageText.homeBottomNav
    }
}

fun RequestState<List<CategoryModel>>.categoryStateListToList(): List<CategoryModel> {

    return if (this is RequestState.Success) {
        this.data
    } else listOf()

}

fun randomCaptcha(
    length: Int
): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}