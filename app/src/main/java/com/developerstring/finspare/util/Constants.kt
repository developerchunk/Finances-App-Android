package com.developerstring.finspare.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.developerstring.finspare.util.state.ProfileAmountType

object Constants {

    // String Joining in List
    const val SEPARATOR_LIST = ","

    // theme settings
    var DARK_THEME_ENABLE by mutableStateOf(false)

    // Transaction Database
    const val TRANSACTION_DB_TABLE = "transaction_table"
    const val TRANSACTION_DB_NAME = "transaction_database"

    // Profile Database Table
    const val PROFIlE_DB_TABLE = "profile_table"
    const val PROFILE_ID = 1

    // Category Database Table
    const val CATEGORY_DB_TABLE = "category_table"

    // OnBoarding
    const val ON_BOARDING_STATUS = "onBoardingStatus"
    const val ON_BOARDING_STATUS_KEY = "on_boarding_status"

    // Profile
    const val PROFILE_DATA = "profileData"
    const val PROFILE_CREATED_STATUS_KEY = "profile_created"

    // message last scan details
    const val MESSAGE_SCAN_ENABLE_KEY = "message_scan_enable"
//    const val MESSAGE_LAST_SCAN_KEY = "message_last_scan"
    const val MESSAGE_ID_LAST_SCAN_KEY = "message_id_last_scan"

//    var LAST_TRANSACTION = ""

    const val DARK_THEME = "dark_theme"
    const val LIGHT_THEME = "light_theme"

    const val INDIAN_CURRENCY = "₹"

    // Chip Selection Add Transaction
    const val SPENT = "spent"
    const val ADD_FUND = "add_fund"
    const val SAVINGS = "savings"

    val ADD_TRANSACTION_TYPE = listOf(
        SPENT,
        ADD_FUND,
        SAVINGS
    )

    val PROFILE_AMOUNT_TYPE = ProfileAmountType.entries.toList()

    const val LEND = "Lend"
    const val THIS_MONTH = "This Month"
    const val LATEST_FIRST = "Latest First"
    const val OLD_FIRST = "Old First"

    var oldFirstFilter: MutableState<Boolean> = mutableStateOf(false)

    val FILTER_NAME = mutableListOf(
        LEND,
        THIS_MONTH,
        LATEST_FIRST,
        OLD_FIRST
    )

    const val OTHER = "Other"
    const val TRANSACTION = "Transaction"
    const val INVESTMENT = "Investment"

    val SUB_CATEGORY = mapOf(
        "Food" to listOf(
            "Breakfast",
            "Lunch",
            "Dinner",
            "Desert",
            "Fruits",
            "Meal",
            "Fast Food",
            "Salad",
            "Snacks"
        ),
        "Beverage" to listOf(
            "Ice-Cream",
            "Water Bottle",
            "Juice",
            "Coffee",
            "Tea",
            "Soft-Drink",
            "Hard-Drink",
        ),
        "Education" to listOf(
            "Book",
            "Software",
            "Stationary",
            "Equipment",
        ),
        "Rent" to listOf(
            "House",
            "Shop",
            "Office",
            "Paying Guest",
            "Vehicles",
            "Ware House",
            "Land",
            "Assets",
            "Plant & Machinery"
        ),
        "Bill" to listOf(
            "Credit Card Bill",
            "DTS Recharge",
            "Mobile Recharge",
            "Electricity Bill",
            "Water Bill",
            "Gas Bill",
            "Wages",
            "OTT Platform Bill"
        ),
        "Fee" to listOf(
            "School",
            "College",
            "Course",
            "Classes",
            "Hostel",
            ),
        "Transaction" to listOf(
            "Bank",
            "Cash",
            "UPI",
            "Card",
            "Net Banking",
            "Online Wallet",
        ),
        "Investment" to listOf(
            "Mutual Funds",
            "Stocks",
            "Policies",
            "SIP",
            "ETFs",
            "Fixed Deposit",
            "Bonds",
            "Real Estate",
            "Gold",
            "Project",
            "Digital Currency"
        ),
        OTHER to listOf(
           OTHER
        ),
    ).toSortedMap()

    const val YES = "YES"
    const val NO = "NO"

    const val ACTIVITY_CHART_SCREEN_KEY = "activity_chart_screen"
    const val CATEGORY_CHART_SCREEN_KEY = "category_chart_screen"

    const val ENGLISH = "English"
    const val MARATHI = "Marathi (मराठी)"
    const val HINDI = "Hindi (हिंदी)"
    const val TELUGU = "Telugu (తెలుగు)"
    const val GUJARATI = "Gujarati (ગુજરાતી)"

    var LANGUAGE = ENGLISH

    val LANGUAGES = listOf(
        ENGLISH,
        MARATHI,
        HINDI,
        TELUGU,
        GUJARATI,
    ).sorted()

    const val HOME_ROUTE = "home"
    const val ACTIVITY_ROUTE = "activity"
    const val PROFILE_ROUTE = "profile"

    val MONTHS = mutableListOf(
        "JAN",
        "FEB",
        "MAR",
        "APR",
        "MAY",
        "JUN",
        "JUL",
        "AUG",
        "SEP",
        "OCT",
        "NOV",
        "DEC",
    )

    const val TERMS_AND_CONDITION_URL = "https://term.finspare.com"

}