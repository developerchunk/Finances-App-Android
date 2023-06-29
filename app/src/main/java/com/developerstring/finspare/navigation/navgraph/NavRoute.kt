package com.developerstring.finspare.navigation.navgraph

import com.developerstring.finspare.util.Constants.ACTIVITY_CHART_SCREEN_KEY
import com.developerstring.finspare.util.Constants.CATEGORY_CHART_SCREEN_KEY

sealed class NavRoute(val route: String) {
    object AddTransactionScreen : NavRoute("add_transaction_screen")
    object ViewHistoryScreen : NavRoute("view_history_screen")
    object TransactionDetailsScreen : NavRoute("transaction_details_screen")
    object EditProfileScreen : NavRoute("edit_profile_screen")
    object EditLanguageScreen : NavRoute("edit_language_screen")
    object EditCategoryScreen : NavRoute("edit_category_screen")
    object EditContactsScreen : NavRoute("edit_contacts_screen")
    object EditContactsDetailScreen : NavRoute("edit_contacts_details_screen")
    object EditCategoryDetailScreen : NavRoute("edit_category_detail_screen")
    object SettingScreen : NavRoute("setting_screen")
    object AboutScreen : NavRoute("about_screen")
    object TermsAndConditionsScreen : NavRoute("terms_and_conditions_screen")

    object ActivityChartScreen : NavRoute(ACTIVITY_CHART_SCREEN_KEY)
    object CategoryChartScreen : NavRoute(CATEGORY_CHART_SCREEN_KEY)
    object AmountChartScreen : NavRoute("amount_chart_screen")
    object ProfileAmountChartScreen : NavRoute("profile_amount_chart_screen")
}
