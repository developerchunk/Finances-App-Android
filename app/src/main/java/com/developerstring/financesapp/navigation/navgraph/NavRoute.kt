package com.developerstring.financesapp.navigation.navgraph

import com.developerstring.financesapp.util.Constants.ACTIVITY_CHART_SCREEN_KEY
import com.developerstring.financesapp.util.Constants.CATEGORY_CHART_SCREEN_KEY

sealed class NavRoute(val route: String) {
    object AddTransactionScreen : NavRoute("add_transaction_screen")
    object ViewHistoryScreen : NavRoute("view_history_screen")
    object TransactionDetailsScreen : NavRoute("transaction_details_screen")
    object EditProfileScreen : NavRoute("edit_profile_screen")

    object ActivityChartScreen : NavRoute(ACTIVITY_CHART_SCREEN_KEY)
    object CategoryChartScreen : NavRoute(CATEGORY_CHART_SCREEN_KEY)
}
