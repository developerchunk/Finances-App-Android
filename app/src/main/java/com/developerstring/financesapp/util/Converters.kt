package com.developerstring.financesapp.util

import com.developerstring.financesapp.util.Constants.ADD_FUND
import com.developerstring.financesapp.util.Constants.SAVINGS
import com.developerstring.financesapp.util.Constants.SPENT

fun keyToTransactionType(key: String): String {
    return when(key) {
        SPENT -> "Spent"
        ADD_FUND -> "Add Fund"
        SAVINGS -> "Savings"
        else -> ""
    }
}