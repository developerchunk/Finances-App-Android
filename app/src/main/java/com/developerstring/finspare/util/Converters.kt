package com.developerstring.finspare.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.util.Constants.ACTIVITY_ROUTE
import com.developerstring.finspare.util.Constants.ADD_FUND
import com.developerstring.finspare.util.Constants.HOME_ROUTE
import com.developerstring.finspare.util.Constants.INDIAN_CURRENCY
import com.developerstring.finspare.util.Constants.LATEST_FIRST
import com.developerstring.finspare.util.Constants.OLD_FIRST
import com.developerstring.finspare.util.Constants.PROFILE_ROUTE
import com.developerstring.finspare.util.Constants.SAVINGS
import com.developerstring.finspare.util.Constants.SPENT
import com.developerstring.finspare.util.Constants.THIS_MONTH
import com.developerstring.finspare.util.Constants.oldFirstFilter
import com.developerstring.finspare.util.state.CategorySortState
import com.developerstring.finspare.util.state.ProfileAmountType
import com.developerstring.finspare.util.state.RequestState
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Calendar
import kotlin.math.abs

fun String.keyToTransactionType(): String {
    return when (this) {
        SPENT -> "Spent"
        ADD_FUND -> "Add Amount"
        SAVINGS -> "Savings"
        ProfileAmountType.MONEY_GIVEN.name -> "Money Given"
        ProfileAmountType.MONEY_TAKEN.name -> "Money Taken"
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

    val simplify: String

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

    val simplify: String

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

    val month = calender.get(Calendar.MONTH) + 1

    return when (this) {
        THIS_MONTH -> "${calender.get(Calendar.YEAR)}${if (month < 10) 0 else ""}$month"
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
            if (currency.last().toString() == INDIAN_CURRENCY)
                simplifyAmountIndia(amount)
            else simplifyAmount(
                amount
            )
        }${currency.last()}"

    }

    fun message(
        category: String,
        subCategory: String,
        transactionMode: String,
        day: Short,
        month: Short,
        year: Short,
    ): String {

        return "Are you sure you want to delete \n\n" +
                "Payment Mode: $transactionMode\n" +
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
    return this.filterNot { it == '<' || it == '>' || it == '/' || it == '#' || it == '\'' || it == '"' || it == '\\' || it == '{' || it == '}' || it == '[' || it == ']' || it == '!' || it == '%' || it == '$' || it == '?' || it == '.' || it == '=' || it == '~' }
        .take(length)
}

fun String.convertStringToInt(): String {
    return this.filter { it in '0'..'9' || it == '-' }.take(10)
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

fun randomCaptcha(
    length: Int
): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

@Composable
fun CategoryListReturn(
    categories: RequestState<List<CategoryModel>>,
    categoryList: (List<CategoryModel>) -> Unit
) {
    if (
        categories is RequestState.Success
    ) {
        categoryList(categories.data)
    }
}

@Composable
fun ProfileListReturn(
    profiles: RequestState<List<ProfileModel>>,
    profileList: (List<ProfileModel>) -> Unit
) {
    if (
        profiles is RequestState.Success
    ) {
        profileList(profiles.data.drop(1))
    }
}

fun List<ProfileModel>.refineProfileModel(
): List<ProfileModel> {

    val list: MutableList<ProfileModel> = mutableListOf()

    this.forEachIndexed { index, profileModel ->
        list.add(
            index = index,
            element = profileModel.copy(ph_no = "", email = "", place = "", extra_info = "")
        )
    }

    return list

}

fun calculateContactAmount(
    profileAmount: Int,
    amount: Int,
    profileAmountType: ProfileAmountType,
    amountType: ProfileAmountType
): Pair<Int, ProfileAmountType> {

    var newProfileAmountType = profileAmountType

    if (amount > profileAmount) {
        newProfileAmountType = amountType
    }

    val newAmount: Int =
        if (amountType == profileAmountType) amount + profileAmount else profileAmount - amount

    return Pair(abs(newAmount), newProfileAmountType)

}

// old contact amount settlement
fun calculateContactAmountOld(
    profileAmount: Int,
    oldAmount: Int,
    newAmount: Int,
    profileAmountType: ProfileAmountType,
    oldAmountType: ProfileAmountType,
    newAmountType: ProfileAmountType,
    values: (Pair<Int, ProfileAmountType>) -> Unit
) {

    val differenceAmount: Int = when (profileAmountType) {
        ProfileAmountType.MONEY_GIVEN -> {
            when (oldAmountType) {
                ProfileAmountType.MONEY_GIVEN -> profileAmount - oldAmount
                ProfileAmountType.MONEY_TAKEN -> profileAmount + oldAmount
            }
        }

        ProfileAmountType.MONEY_TAKEN -> {
            when (oldAmountType) {
                ProfileAmountType.MONEY_GIVEN -> profileAmount + oldAmount
                ProfileAmountType.MONEY_TAKEN -> profileAmount - oldAmount
            }
        }
    }

    val differenceProfileAmountType: ProfileAmountType = if (oldAmount > profileAmount) {
        oldAmountType
    } else profileAmountType

    values(
        calculateContactAmount(
            profileAmountType = differenceProfileAmountType,
            profileAmount = differenceAmount,
            amount = newAmount,
            amountType = newAmountType
        )
    )

}

fun String.stringToProfileAmountType(): ProfileAmountType {

    return when (this) {
        ProfileAmountType.MONEY_TAKEN.name -> ProfileAmountType.MONEY_TAKEN
        ProfileAmountType.MONEY_GIVEN.name -> ProfileAmountType.MONEY_GIVEN
        else -> ProfileAmountType.MONEY_GIVEN
    }

}

fun profileAmountChartData(
    totalAmount: Int,
    profiles: List<ProfileModel>,
    context: Context,
    languageText: LanguageText
): Map<String, Long> {

    var amountTaken by mutableStateOf(0L)
    var amountGiven by mutableStateOf(0L)
    val map = mutableMapOf<String, Long>()

    amountTaken = 0L
    amountGiven = 0L

    profiles.forEach {
        when (it.amount_type.stringToProfileAmountType()) {
            ProfileAmountType.MONEY_GIVEN -> amountGiven += it.total_amount
            ProfileAmountType.MONEY_TAKEN -> amountTaken += it.total_amount
        }
    }

    map["Your Balance"] = totalAmount - amountTaken
    map[context.getString(languageText.moneyTaken)] = amountTaken
    map[context.getString(languageText.moneyGiven)] = amountGiven

    return map

}

fun Int.formatNumberingStyle(currency: String): String {

    if (currency != INDIAN_CURRENCY) {
        return NumberFormat.getNumberInstance().format(this)
    }

    val decimalFormatSymbols = DecimalFormatSymbols().apply {
        groupingSeparator = ','
    }
    val decimalFormat = DecimalFormat("#,##,###", decimalFormatSymbols)

    return decimalFormat.format(this)
}

fun String.formatNumberingStyleToInt(): Int {

    if (this.isEmpty()) return 0

    return try {
        val value = this.convertStringToInt().filterNot { it=='-' }
        value.toInt()
    } catch (_: Exception) {
        0
    }

}

fun profilesToPieChartData(profiles: List<ProfileModel>): MutableMap<String, Long> {

    if (profiles.isEmpty()) {
        return mutableMapOf()
    }

    val map = mutableMapOf<String, Long>()

    profiles.forEachIndexed { index, profileModel ->
        map[index.toString()] = profileModel.total_amount.toLong()
    }

    return map

}