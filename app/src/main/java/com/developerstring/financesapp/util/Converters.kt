package com.developerstring.financesapp.util

import com.developerstring.financesapp.util.Constants.ADD_FUND
import com.developerstring.financesapp.util.Constants.SAVINGS
import com.developerstring.financesapp.util.Constants.SPENT
import java.text.DecimalFormat
import kotlin.math.abs

fun keyToTransactionType(key: String): String {
    return when(key) {
        SPENT -> "Spent"
        ADD_FUND -> "Add Fund"
        SAVINGS -> "Savings"
        else -> ""
    }
}

fun transactionTypeToSymbol(transactionType: String): String {
    return when(transactionType) {
        ADD_FUND -> "+"
        SPENT -> "-"
        SAVINGS -> ""
        else -> "-/+"
    }
}

fun simplifyAmount(amount: Int) : String {
    val am: Float

    val df = DecimalFormat("##.##")

    var simplify = ""

    if (abs(amount/1000000) >=1) {
        am = amount.toFloat()/1000000
        simplify = df.format(am) + "M"
    } else if (abs(amount/1000) >= 1) {
        am = amount.toFloat()/1000
        simplify = df.format(am) + "K"
    } else {
        simplify = amount.toString()
    }

    return simplify
}

fun simplifyAmountIndia(amount: Int) : String {
    val am: Float

    val df = DecimalFormat("##.##")

    var simplify = ""

    if (abs(amount/10000000) >=1) {
        am = amount.toFloat()/10000000
        simplify = df.format(am) + "Cr"
    } else if (abs(amount/100000) >= 1) {
        am = amount.toFloat()/100000
        simplify = df.format(am) + "L"
    } else if (abs(amount/1000) >= 1) {
        am = amount.toFloat()/1000
        simplify = df.format(am) + "K"
    } else {
        simplify = amount.toString()
    }

    return simplify
}