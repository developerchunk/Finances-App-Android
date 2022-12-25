package com.developerstring.financesapp.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.developerstring.financesapp.R
import com.developerstring.financesapp.ui.theme.Dark
import com.developerstring.financesapp.ui.theme.DarkGreen
import com.developerstring.financesapp.ui.theme.Green
import com.developerstring.financesapp.ui.theme.UIBlue
import com.developerstring.financesapp.util.dataclass.ActivityCardData

object Constants {

    // theme settings
    var DARK_THEME_ENABLE = true

    // Transaction Database
    const val TRANSACTION_DB_TABLE = "transaction_table"
    const val TRANSACTION_DB_NAME = "transaction_database"

    // Profile Database Table
    const val PROFIlE_DB_TABLE = "profile_table"
    const val PROFILE_ID = 1

    // OnBoarding
    const val ON_BOARDING_STATUS = "onBoardingStatus"
    const val ON_BOARDING_STATUS_KEY = "on_boarding_status"

    // Profile
    const val PROFILE_DATA = "profileData"
    const val PROFILE_NAME_KEY = "name"
    const val PROFILE_TOTAL_AMOUNT_KEY = "total_amount"
    const val PROFILE_CURRENCY_KEY = "currency"
    const val PROFILE_MONTHLY_SPENDING_KEY = "monthly_spending"
    const val PROFILE_MONTHLY_SAVINGS_KEY = "monthly_savings"
    const val PROFILE_CREATED_STATUS_KEY = "profile_created"

    // Themes
    const val THEME_SETTING_KEY = "theme_setting"


//    var LAST_TRANSACTION = ""

    const val DARK_THEME = "dark_theme"
    const val LIGHT_THEME = "light_theme"
    const val PINK_THEME = "pink_theme"

    const val DARK_THEME_TEXT = "Dark Theme"
    const val PROFILE_TEXT = "Profile"
    const val MANAGE_DATA_TEXT = "Manage Data"

    val PROFILE_CONTENT_LIST = mapOf(
        PROFILE_TEXT to R.drawable.profile_edit,
        MANAGE_DATA_TEXT to R.drawable.setting_icon,
        DARK_THEME_TEXT to R.drawable.themes_icon,
    )

    const val INDIAN_CURRENCY = "₹"
    var CURRENCY = "₹"

    // Chip Selection Add Transaction
    const val SPENT = "spent"
    const val ADD_FUND = "add_fund"
    const val SAVINGS = "savings"

    val ADD_TRANSACTION_TYPE = listOf(
        SPENT,
        ADD_FUND,
        SAVINGS
    )

    const val THIS_MONTH = "This Month"
    const val LATEST_FIRST = "Latest First"
    const val OLD_FIRST = "Old First"

    var oldFirstFilter: MutableState<Boolean> = mutableStateOf(false)

    val FILTER_NAME = mutableListOf(
        THIS_MONTH,
        LATEST_FIRST,
        OLD_FIRST
    )

//    const val CATEGORY_LIST_KEY = "category"
//    const val SUB_CATEGORY_LIST_KEY = "sub_category"
    const val OTHER = "Other"


    // Category List
    val CATEGORIES = listOf(
        "Food",
        "Beverages",
        "Sports",
        "Education",
        "Travel",
        "Rent",
        "Bills",
        "Fees",
        "Transaction",
        "Gift",
        "Other",
        "Drinks"
    ).sorted()

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
            "Healthy Meal",
        ),
        "Beverages" to listOf(
            "Chips",
            "Chocolates",
            "Packets",
            "Drinks",
            "Ice-Cream",
            "Water Bottle",
        ),
        "Sports" to listOf(
            "Equipments",
            "Fees",
            "Sports Wear",
        ),
        "Education" to listOf(
            "Course",
            "Fees",
            "Books",
            "Stationary",
        ),
        "Travel" to listOf(
            "Fees",
            "Equipments",
            "Stationary",
            "Cloths",
            "Backpacks",
            "Shoes",
        ),
        "Rent" to listOf(
            "House",
            "Shop",
        ),
        "Bills" to listOf(
            "Recharge",
            "Electric Bill",
            "Water Bill",
            "Gas Bill",
        ),
        "Fees" to listOf(
            "School",
            "College",
            "Course",
            "Classes",
            "Dance Class",
            "Music Class",
        ),
        "Transaction" to listOf(
            "Add Amount",
            "Bank",
            "Cash",
            "UPI",
            "Card",
            "Net Banking",
        ),
        "Gift" to listOf(
            "Money",
            "Objects",
        ),
        "Drinks" to listOf(
            "Coco-Cola",
            "Pepsi",
            "Cold Drinks",
            "Fruit Juice",
        ),
        "Other" to listOf(
           "Other"
        ),
    )

    const val YES = "YES"
    const val NO = "NO"

    const val ACTIVITY_CHART_SCREEN_KEY = "activity_chart_screen"
    const val CATEGORY_CHART_SCREEN_KEY = "category_chart_screen"

    val ActivityCardContent = listOf(
        ActivityCardData(
            text = "Activity\nChart",
            icon = R.drawable.chart_icon,
            bgColor = Dark,
            cardColor = UIBlue,
            key = ACTIVITY_CHART_SCREEN_KEY
        ),
        ActivityCardData(
            text = "Category\nChart",
            icon = R.drawable.category_icon,
            bgColor = DarkGreen,
            cardColor = Green,
            key = CATEGORY_CHART_SCREEN_KEY
        ),
    )

}