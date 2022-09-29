package com.developerstring.financesapp.util

object Constants {

    // Database
    const val DATABASE_TABLE = "transaction_table"
    const val DATABASE_NAME = "transaction_database"

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

    // Currency
    val CURRENCY_LIST = listOf(
        "Indian Rupee- ₹",
        "Dollar- $",
        "Euro- €"
    )

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

    // Category List
    val CATEGORIES = listOf(
        "Food",
        "Beverages",
        "Sports",
        "Learning",
        "Travel",
        "Rent",
        "Bills",
        "Fees",
        "Bank",
        "Cash",
        "Other"
    ).sorted()

    const val YES = "YES"
    const val NO = "NO"
}