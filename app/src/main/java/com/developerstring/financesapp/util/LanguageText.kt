package com.developerstring.financesapp.util

import com.developerstring.financesapp.R
import com.developerstring.financesapp.ui.theme.Dark
import com.developerstring.financesapp.ui.theme.DarkGreen
import com.developerstring.financesapp.ui.theme.Green
import com.developerstring.financesapp.ui.theme.UIBlue
import com.developerstring.financesapp.util.Constants.ENGLISH
import com.developerstring.financesapp.util.Constants.GUJARATI
import com.developerstring.financesapp.util.Constants.HINDI
import com.developerstring.financesapp.util.Constants.MARATHI
import com.developerstring.financesapp.util.Constants.TELUGU
import com.developerstring.financesapp.util.dataclass.ActivityCardData

class LanguageText(
    language: String
) {

    //
    val homeBottomNav = when (Constants.LANGUAGE) {
        ENGLISH -> R.string.home_bottom_nav
        MARATHI -> R.string.home_bottom_nav_marathi
        TELUGU -> R.string.home_bottom_nav_telugu
        GUJARATI -> R.string.home_bottom_nav_gujarati
        HINDI -> R.string.home_bottom_nav_hindi
        else -> R.string.home_bottom_nav
    }
    val activityBottomNav = when (Constants.LANGUAGE) {
        ENGLISH -> R.string.activity_bottom_nav
        MARATHI -> R.string.activity_bottom_nav_marathi
        TELUGU -> R.string.activity_bottom_nav_telugu
        GUJARATI -> R.string.activity_bottom_nav_gujarati
        HINDI -> R.string.activity_bottom_nav_hindi
        else -> R.string.activity_bottom_nav
    }
    val profileBottomNav = when (Constants.LANGUAGE) {
        ENGLISH -> R.string.profile_bottom_nav
        MARATHI -> R.string.profile_bottom_nav_marathi
        TELUGU -> R.string.profile_bottom_nav_telugu
        GUJARATI -> R.string.profile_bottom_nav_gujarati
        HINDI -> R.string.profile_bottom_nav_hindi
        else -> R.string.profile_bottom_nav
    }

    //
    val createProfileScreenText1 = when (language) {
        ENGLISH -> R.string.create_profile_screen_text_1
        MARATHI -> R.string.create_profile_screen_text_1_marathi
        TELUGU -> R.string.create_profile_screen_text_1_telugu
        GUJARATI -> R.string.create_profile_screen_text_1_gujarati
        HINDI -> R.string.create_profile_screen_text_1_hindi
        else -> R.string.create_profile_screen_text_1
    }
    val createProfileScreenText2 = when (language) {
        ENGLISH -> R.string.create_profile_screen_text_2
        MARATHI -> R.string.create_profile_screen_text_2_marathi
        TELUGU -> R.string.create_profile_screen_text_2_telugu
        GUJARATI -> R.string.create_profile_screen_text_2_gujarati
        HINDI -> R.string.create_profile_screen_text_2_hindi
        else -> R.string.create_profile_screen_text_2
    }
    val createProfileScreenName = when (language) {
        ENGLISH -> R.string.create_profile_screen_name
        MARATHI -> R.string.create_profile_screen_name_marathi
        TELUGU -> R.string.create_profile_screen_name_telugu
        GUJARATI -> R.string.create_profile_screen_name_gujarati
        HINDI -> R.string.create_profile_screen_name_hindi
        else -> R.string.create_profile_screen_name
    }
    val createProfileScreenAmount = when (language) {
        ENGLISH -> R.string.create_profile_screen_amount
        MARATHI -> R.string.create_profile_screen_amount_marathi
        TELUGU -> R.string.create_profile_screen_amount_telugu
        GUJARATI -> R.string.create_profile_screen_amount_gujarati
        HINDI -> R.string.create_profile_screen_amount_hindi
        else -> R.string.create_profile_screen_amount
    }
    val createProfileScreenCurrency = when (language) {
        ENGLISH -> R.string.create_profile_screen_currency
        MARATHI -> R.string.create_profile_screen_currency_marathi
        TELUGU -> R.string.create_profile_screen_currency_telugu
        GUJARATI -> R.string.create_profile_screen_currency_gujarati
        HINDI -> R.string.create_profile_screen_currency_hindi
        else -> R.string.create_profile_screen_currency
    }
    val createProfileScreen2Spending = when (language) {
        ENGLISH -> R.string.create_profile_screen_2_spending
        MARATHI -> R.string.create_profile_screen_2_spending_marathi
        TELUGU -> R.string.create_profile_screen_2_spending_telugu
        GUJARATI -> R.string.create_profile_screen_2_spending_gujarati
        HINDI -> R.string.create_profile_screen_2_spending_hindi
        else -> R.string.create_profile_screen_2_spending
    }
    val createProfileScreen2Saving = when (language) {
        ENGLISH -> R.string.create_profile_screen_2_savings
        MARATHI -> R.string.create_profile_screen_2_savings_marathi
        TELUGU -> R.string.create_profile_screen_2_savings_telugu
        GUJARATI -> R.string.create_profile_screen_2_savings_gujarati
        HINDI -> R.string.create_profile_screen_2_savings_hindi
        else -> R.string.create_profile_screen_2_savings
    }

    //
    val profile = when (language) {
        ENGLISH -> R.string.profile
        MARATHI -> R.string.profile_marathi
        TELUGU -> R.string.profile_telugu
        GUJARATI -> R.string.profile_gujarati
        HINDI -> R.string.profile_hindi
        else -> R.string.profile
    }
    val totalBalance = when (language) {
        ENGLISH -> R.string.total_balance
        MARATHI -> R.string.total_balance_marathi
        TELUGU -> R.string.total_balance_telugu
        GUJARATI -> R.string.total_balance_gujarati
        HINDI -> R.string.total_balance_hindi
        else -> R.string.total_balance
    }
    val addPayment = when (language) {
        ENGLISH -> R.string.add_payment
        MARATHI -> R.string.add_payment_marathi
        TELUGU -> R.string.add_payment_telugu
        GUJARATI -> R.string.add_payment_gujarati
        HINDI -> R.string.add_payment_hindi
        else -> R.string.add_payment
    }
    val paymentHistory = when (language) {
        ENGLISH -> R.string.history
        MARATHI -> R.string.payment_history_marathi
        TELUGU -> R.string.payment_telugu
        GUJARATI -> R.string.history_gujarati
        HINDI -> R.string.history_hindi
        else -> R.string.history
    }
    val history = when (language) {
        ENGLISH -> R.string.history
        MARATHI -> R.string.history_marathi
        TELUGU -> R.string.history_telugu
        GUJARATI -> R.string.history_gujarati
        HINDI -> R.string.history_hindi
        else -> R.string.history
    }
    val spent = when (language) {
        ENGLISH -> R.string.spent
        MARATHI -> R.string.spent_marathi
        TELUGU -> R.string.spent_telugu
        GUJARATI -> R.string.spent_gujarati
        HINDI -> R.string.spent_hindi
        else -> R.string.spent
    }
    val saving = when (language) {
        ENGLISH -> R.string.saving
        MARATHI -> R.string.saving_marathi
        TELUGU -> R.string.saving_telugu
        GUJARATI -> R.string.saving_gujarati
        HINDI -> R.string.saving_hindi
        else -> R.string.saving
    }
    val myActivity = when (language) {
        ENGLISH -> R.string.my_activity
        MARATHI -> R.string.my_activity_marathi
        TELUGU -> R.string.my_activity_telugu
        GUJARATI -> R.string.my_activity_gujarati
        HINDI -> R.string.my_activity_hindi
        else -> R.string.my_activity
    }
    private val activityChart = when (language) {
        ENGLISH -> R.string.activity_chart
        MARATHI -> R.string.activity_chart_marathi
        TELUGU -> R.string.activity_chart_telugu
        GUJARATI -> R.string.activity_chart_gujarati
        HINDI -> R.string.activity_chart_hindi
        else -> R.string.my_activity
    }
    val categoriesChart = when (language) {
        ENGLISH -> R.string.categories_chart
        MARATHI -> R.string.categories_chart_marathi
        TELUGU -> R.string.categories_chart_telugu
        GUJARATI -> R.string.categories_chart_gujarati
        HINDI -> R.string.categories_chart_hindi
        else -> R.string.categories_chart
    }
    val lastWeek = when (language) {
        ENGLISH -> R.string.last_week
        MARATHI -> R.string.last_week_marathi
        TELUGU -> R.string.last_week_telugu
        GUJARATI -> R.string.last_week_gujarati
        HINDI -> R.string.last_week_hindi
        else -> R.string.last_week
    }
    val spentThisWeek = when (language) {
        ENGLISH -> R.string.spent_this_week
        MARATHI -> R.string.spent_this_week_marathi
        TELUGU -> R.string.spent_this_week_telugu
        GUJARATI -> R.string.spent_this_week_gujarati
        HINDI -> R.string.spent_this_week_hindi
        else -> R.string.spent_this_week
    }
    val sortBy = when (language) {
        ENGLISH -> R.string.sort_by
        MARATHI -> R.string.sort_by_marathi
        TELUGU -> R.string.sort_by_telugu
        GUJARATI -> R.string.sort_by_gujarati
        HINDI -> R.string.sort_by_hindi
        else -> R.string.sort_by
    }
    val yes = when (language) {
        ENGLISH -> R.string.yes
        MARATHI -> R.string.yes_marathi
        TELUGU -> R.string.yes_telugu
        GUJARATI -> R.string.yes_gujarati
        HINDI -> R.string.yes_hindi
        else -> R.string.yes
    }
    val no = when (language) {
        ENGLISH -> R.string.no
        MARATHI -> R.string.no_marathi
        TELUGU -> R.string.no_telugu
        GUJARATI -> R.string.no_gujarati
        HINDI -> R.string.no_hindi
        else -> R.string.no
    }

    val activityChartText = when (language) {
        ENGLISH -> R.string.activity_chart_text
        MARATHI -> R.string.activity_chart_marathi
        TELUGU -> R.string.activity_chart_text_telugu
        GUJARATI -> R.string.activity_chart_text_gujarati
        HINDI -> R.string.activity_chart_text_hindi
        else -> R.string.activity_chart
    }

    //
    val amountTextField = when (language) {
        ENGLISH -> R.string.amount_text_field
        MARATHI -> R.string.amount_text_field_marathi
        TELUGU -> R.string.amount_text_field_telugu
        GUJARATI -> R.string.amount_text_field_gujarati
        HINDI -> R.string.amount_text_field_hindi
        else -> R.string.amount_text_field
    }
    val categoryTextField = when (language) {
        ENGLISH -> R.string.category_text_field
        MARATHI -> R.string.category_text_field_marathi
        TELUGU -> R.string.category_text_field_telugu
        GUJARATI -> R.string.category_text_field_gujarati
        HINDI -> R.string.category_text_field_hindi
        else -> R.string.category_text_field
    }
    val otherCategoryTextField = when (language) {
        ENGLISH -> R.string.other_category_text_field
        MARATHI -> R.string.other_category_text_field_marathi
        TELUGU -> R.string.other_category_text_field_telugu
        GUJARATI -> R.string.other_category_text_field_gujarati
        HINDI -> R.string.other_category_text_field_hindi
        else -> R.string.other_category_text_field
    }
    val subCategoryTextField = when (language) {
        ENGLISH -> R.string.sub_category_text_field
        MARATHI -> R.string.sub_category_text_field_marathi
        TELUGU -> R.string.sub_category_text_field_telugu
        GUJARATI -> R.string.sub_category_text_field_gujarati
        HINDI -> R.string.sub_category_text_field_hindi
        else -> R.string.sub_category_text_field
    }
    val otherSubCategoryTextField = when (language) {
        ENGLISH -> R.string.other_sub_category_text_field
        MARATHI -> R.string.other_sub_category_text_field_marathi
        TELUGU -> R.string.other_sub_category_text_field_telugu
        GUJARATI -> R.string.other_sub_category_text_field_gujarati
        HINDI -> R.string.other_sub_category_text_field_hindi
        else -> R.string.other_sub_category_text_field
    }
    val dateTextField = when (language) {
        ENGLISH -> R.string.date_text_field
        MARATHI -> R.string.date_text_field_marathi
        TELUGU -> R.string.date_text_field_telugu
        GUJARATI -> R.string.date_text_field_gujarati
        HINDI -> R.string.date_text_field_hindi
        else -> R.string.date_text_field
    }
    val extraInfoTextField = when (language) {
        ENGLISH -> R.string.extra_info_text_field
        MARATHI -> R.string.extra_info_text_field_marathi
        TELUGU -> R.string.extra_info_text_field_telugu
        GUJARATI -> R.string.extra_info_text_field_gujarati
        HINDI -> R.string.extra_info_text_field_hindi
        else -> R.string.extra_info_text_field
    }
    val placeInfoTextField = when (language) {
        ENGLISH -> R.string.place_info_text_field
        MARATHI -> R.string.place_info_text_field_marathi
        TELUGU -> R.string.place_info_text_field_telugu
        GUJARATI -> R.string.place_info_text_field_gujarati
        HINDI -> R.string.place_info_text_field_hindi
        else -> R.string.place_info_text_field
    }

    val transactionModeTextField = when (language) {
        ENGLISH -> R.string.transaction_mode_text_field
        MARATHI -> R.string.transaction_mode_text_field_marathi
        TELUGU -> R.string.transaction_mode_text_field_telugu
        GUJARATI -> R.string.transaction_mode_text_field_gujarati
        HINDI -> R.string.transaction_mode_text_field_hindi
        else -> R.string.transaction_mode_text_field
    }

    val transactionModeOptionalTextField = when (language) {
        ENGLISH -> R.string.other_transaction_mode_text_field
        MARATHI -> R.string.other_transaction_mode_text_field_marathi
        TELUGU -> R.string.other_transaction_mode_text_field_telugu
        GUJARATI -> R.string.other_transaction_mode_text_field_gujarati
        HINDI -> R.string.other_transaction_mode_text_field_hindi
        else -> R.string.other_transaction_mode_text_field
    }

    val timeTextField = when (language) {
        ENGLISH -> R.string.time_text_field
        MARATHI -> R.string.time_text_field_marathi
        TELUGU -> R.string.time_text_field_telugu
        GUJARATI -> R.string.time_text_field_gujarati
        HINDI -> R.string.time_text_field_hindi
        else -> R.string.time_text_field
    }

    //
//    val search = when (language) {
//        ENGLISH -> R.string.search
//        MARATHI -> R.string.search_marathi
//        else -> R.string.search
//    }
    val next = when (language) {
        ENGLISH -> R.string.next
        MARATHI -> R.string.next_marathi
        TELUGU -> R.string.next_telugu
        GUJARATI -> R.string.next_gujarati
        HINDI -> R.string.next_hindi
        else -> R.string.next
    }
    val searchPlaceholder = when (language) {
        ENGLISH -> R.string.search_placeholder
        MARATHI -> R.string.search_placeholder_marathi
        TELUGU -> R.string.search_placeholder_telugu
        GUJARATI -> R.string.search_placeholder_gujarati
        HINDI -> R.string.search_placeholder_hindi
        else -> R.string.search_placeholder
    }
    val finish = when (language) {
        ENGLISH -> R.string.finish
        MARATHI -> R.string.finish_marathi
        TELUGU -> R.string.finish_telugu
        GUJARATI -> R.string.finish_gujarati
        HINDI -> R.string.finish_hindi
        else -> R.string.finish
    }
    val payment = when (language) {
        ENGLISH -> R.string.payment
        MARATHI -> R.string.payment_marathi
        TELUGU -> R.string.payment_telugu
        GUJARATI -> R.string.payment_gujarati
        HINDI -> R.string.payment_hindi
        else -> R.string.payment
    }

    val month = when (language) {
        ENGLISH -> R.string.month
        MARATHI -> R.string.month_marathi
        TELUGU -> R.string.month_telugu
        GUJARATI -> R.string.month_gujarati
        HINDI -> R.string.month_hindi
        else -> R.string.month
    }
    val quarter = when (language) {
        ENGLISH -> R.string.quarter
        MARATHI -> R.string.quarter_marathi
        TELUGU -> R.string.quarter_telugu
        GUJARATI -> R.string.quarter_gujarati
        HINDI -> R.string.quarter_hindi
        else -> R.string.quarter
    }

    val save = when (language) {
        ENGLISH -> R.string.save
        MARATHI -> R.string.save_marathi
        TELUGU -> R.string.save_telugu
        GUJARATI -> R.string.save_gujarati
        HINDI -> R.string.save_hindi
        else -> R.string.save
    }

    val darkTheme = when (language) {
        ENGLISH -> R.string.dark_theme
        MARATHI -> R.string.dark_theme_marathi
        TELUGU -> R.string.dark_theme_telugu
        GUJARATI -> R.string.dark_theme_gujarati
        HINDI -> R.string.dark_theme_hindi
        else -> R.string.dark_theme
    }

    val settings = when (language) {
        ENGLISH -> R.string.settings
        MARATHI -> R.string.settings_marathi
        TELUGU -> R.string.settings_telugu
        GUJARATI -> R.string.settings_gujarati
        HINDI -> R.string.settings_hindi
        else -> R.string.settings
    }

    val timeFormat = when (language) {
        ENGLISH -> R.string.time_format
        MARATHI -> R.string.time_format_marathi
        TELUGU -> R.string.time_format_telugu
        GUJARATI -> R.string.time_format_gujarati
        HINDI -> R.string.time_format_hindi
        else -> R.string.time_format
    }

    val resetCategories = when (language) {
        ENGLISH -> R.string.reset_categories
        MARATHI -> R.string.reset_categories_marathi
        TELUGU -> R.string.reset_categories_telugu
        GUJARATI -> R.string.reset_categories_gujarati
        HINDI -> R.string.reset_categories_hindi
        else -> R.string.reset_categories
    }

    val deleteAllTransaction = when (language) {
        ENGLISH -> R.string.delete_all_transactions
        MARATHI -> R.string.delete_all_transactions_marathi
        TELUGU -> R.string.delete_all_transactions_telugu
        GUJARATI -> R.string.delete_all_transactions_gujarati
        HINDI -> R.string.delete_all_transactions_hindi
        else -> R.string.delete_all_transactions
    }

    val deleteProfile = when (language) {
        ENGLISH -> R.string.delete_profile
        MARATHI -> R.string.delete_profile_marathi
        TELUGU -> R.string.delete_profile_telugu
        GUJARATI -> R.string.delete_profile_gujarati
        HINDI -> R.string.delete_profile_hindi
        else -> R.string.delete_profile
    }

    val languageText = when (language) {
        ENGLISH -> R.string.language
        MARATHI -> R.string.language_marathi
        TELUGU -> R.string.language_telugu
        GUJARATI -> R.string.language_gujarati
        HINDI -> R.string.language_hindi
        else -> R.string.language
    }

    // ActivityScreen Charts
    val activityCardContent = listOf(
        ActivityCardData(
            text = activityChart,
            icon = R.drawable.chart_icon,
            bgColor = Dark,
            cardColor = UIBlue,
            key = Constants.ACTIVITY_CHART_SCREEN_KEY
        ),
        ActivityCardData(
            text = categoriesChart,
            icon = R.drawable.category_icon,
            bgColor = DarkGreen,
            cardColor = Green,
            key = Constants.CATEGORY_CHART_SCREEN_KEY
        ),
    )

    var profileContentList = mapOf(
        profile to R.drawable.profile_edit,
        settings to R.drawable.setting_icon,
        darkTheme to R.drawable.themes_icon,
        languageText to R.drawable.language_icon,
    )

    val settingsList = mapOf(
        timeFormat to R.drawable.profile_edit,
        resetCategories to R.drawable.reset_icon,
        deleteAllTransaction to R.drawable.delete_outline_icon,
        deleteProfile to R.drawable.profile_delete,
    )

}