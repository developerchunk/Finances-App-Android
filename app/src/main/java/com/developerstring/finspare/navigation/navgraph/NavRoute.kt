package com.developerstring.finspare.navigation.navgraph

import com.developerstring.finspare.util.Constants.ACTIVITY_CHART_SCREEN_KEY
import com.developerstring.finspare.util.Constants.CATEGORY_CHART_SCREEN_KEY

sealed class NavRoute(val route: String) {
    data object AddTransactionScreen : NavRoute("add_transaction_screen")
    data object ViewHistoryScreen : NavRoute("view_history_screen")
    data object TransactionDetailsScreen : NavRoute("transaction_details_screen")
    data object EditProfileScreen : NavRoute("edit_profile_screen")
    data object EditLanguageScreen : NavRoute("edit_language_screen")
    data object EditCategoryScreen : NavRoute("edit_category_screen")
    data object EditContactsScreen : NavRoute("edit_contacts_screen")
    data object EditContactsDetailScreen : NavRoute("edit_contacts_details_screen")
    data object EditCategoryDetailScreen : NavRoute("edit_category_detail_screen")
    data object SettingScreen : NavRoute("setting_screen")
    data object AboutScreen : NavRoute("about_screen")
    data object TermsAndConditionsScreen : NavRoute("terms_and_conditions_screen")
    data object AutoPaymentScreen : NavRoute("auto_payment_screen")

    data object ActivityChartScreen : NavRoute(ACTIVITY_CHART_SCREEN_KEY)
    data object CategoryChartScreen : NavRoute(CATEGORY_CHART_SCREEN_KEY)
    data object AmountChartScreen : NavRoute("amount_chart_screen")
    data object ProfileAmountChartScreen : NavRoute("profile_amount_chart_screen")
    data object MessagePaymentInfoScreen : NavRoute("message_payment_info_screen")
}
