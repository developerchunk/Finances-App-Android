package com.developerstring.financesapp.util

import com.developerstring.financesapp.R
import com.developerstring.financesapp.ui.theme.Dark
import com.developerstring.financesapp.ui.theme.DarkGreen
import com.developerstring.financesapp.ui.theme.Green
import com.developerstring.financesapp.ui.theme.UIBlue
import com.developerstring.financesapp.util.Constants.ENGLISH
import com.developerstring.financesapp.util.Constants.MARATHI
import com.developerstring.financesapp.util.dataclass.ActivityCardData

class LanguageText(
    language: String
) {

    val activityCardContent = listOf(
        ActivityCardData(
            text = when (language) {
                ENGLISH -> R.string.activity_chart
                MARATHI -> R.string.activity_chart_marathi
                else -> R.string.activity_chart
            },
            icon = R.drawable.chart_icon,
            bgColor = Dark,
            cardColor = UIBlue,
            key = Constants.ACTIVITY_CHART_SCREEN_KEY
        ),
        ActivityCardData(
            text = when (language) {
                ENGLISH -> R.string.categories_chart
                MARATHI -> R.string.categories_chart_marathi
                else -> R.string.categories_chart
            },
            icon = R.drawable.category_icon,
            bgColor = DarkGreen,
            cardColor = Green,
            key = Constants.CATEGORY_CHART_SCREEN_KEY
        ),
    )

    //
    val homeBottomNav = when (Constants.LANGUAGE) {
        ENGLISH -> R.string.home_bottom_nav
        MARATHI -> R.string.home_bottom_nav_marathi
        else -> R.string.home_bottom_nav
    }
    val activityBottomNav = when (Constants.LANGUAGE) {
        ENGLISH -> R.string.activity_bottom_nav
        MARATHI -> R.string.activity_bottom_nav_marathi
        else -> R.string.activity_bottom_nav
    }
    val profileBottomNav = when (Constants.LANGUAGE) {
        ENGLISH -> R.string.profile_bottom_nav
        MARATHI -> R.string.profile_bottom_nav_marathi
        else -> R.string.profile_bottom_nav
    }

    //
    val createProfileScreenText1 = when (language) {
        ENGLISH -> R.string.create_profile_screen_text_1
        MARATHI -> R.string.create_profile_screen_text_1_marathi
        else -> R.string.create_profile_screen_text_1
    }
    val createProfileScreenText2 = when (language) {
        ENGLISH -> R.string.create_profile_screen_text_2
        MARATHI -> R.string.create_profile_screen_text_2_marathi
        else -> R.string.create_profile_screen_text_2
    }
    val createProfileScreenName = when (language) {
        ENGLISH -> R.string.create_profile_screen_name
        MARATHI -> R.string.create_profile_screen_name_marathi
        else -> R.string.create_profile_screen_name
    }
    val createProfileScreenAmount = when (language) {
        ENGLISH -> R.string.create_profile_screen_amount
        MARATHI -> R.string.create_profile_screen_amount_marathi
        else -> R.string.create_profile_screen_amount
    }
    val createProfileScreenCurrency = when (language) {
        ENGLISH -> R.string.create_profile_screen_currency
        MARATHI -> R.string.create_profile_screen_currency_marathi
        else -> R.string.create_profile_screen_currency
    }
    val createProfileScreen2Spending = when (language) {
        ENGLISH -> R.string.create_profile_screen_2_spending
        MARATHI -> R.string.create_profile_screen_2_spending_marathi
        else -> R.string.create_profile_screen_2_spending
    }
    val createProfileScreen2Saving = when (language) {
        ENGLISH -> R.string.create_profile_screen_2_savings
        MARATHI -> R.string.create_profile_screen_2_savings_marathi
        else -> R.string.create_profile_screen_2_savings
    }

    //
    val profile = when (language) {
        ENGLISH -> R.string.profile
        MARATHI -> R.string.profile_marathi
        else -> R.string.profile
    }
    val totalBalance = when (language) {
        ENGLISH -> R.string.total_balance
        MARATHI -> R.string.total_balance_marathi
        else -> R.string.total_balance
    }
    val addPayment = when (language) {
        ENGLISH -> R.string.add_payment
        MARATHI -> R.string.add_payment_marathi
        else -> R.string.add_payment
    }
    val history = when (language) {
        ENGLISH -> R.string.history
        MARATHI -> R.string.history_marathi
        else -> R.string.history
    }
    val spent = when (language) {
        ENGLISH -> R.string.spent
        MARATHI -> R.string.spent_marathi
        else -> R.string.spent
    }
    val saving = when (language) {
        ENGLISH -> R.string.saving
        MARATHI -> R.string.saving_marathi
        else -> R.string.spent
    }
    val myActivity = when (language) {
        ENGLISH -> R.string.my_activity
        MARATHI -> R.string.my_activity_marathi
        else -> R.string.my_activity
    }
    val activityChart = when (language) {
        ENGLISH -> R.string.activity_chart
        MARATHI -> R.string.activity_chart_marathi
        else -> R.string.my_activity
    }
    val categoriesChart = when (language) {
        ENGLISH -> R.string.categories_chart
        MARATHI -> R.string.categories_chart_marathi
        else -> R.string.categories_chart
    }
    val lastWeek = when (language) {
        ENGLISH -> R.string.last_week
        MARATHI -> R.string.last_week_marathi
        else -> R.string.last_week
    }
    val spentThisWeek = when (language) {
        ENGLISH -> R.string.spent_this_week
        MARATHI -> R.string.spent_this_week_marathi
        else -> R.string.spent_this_week
    }
    val sortBy = when (language) {
        ENGLISH -> R.string.sort_by
        MARATHI -> R.string.sort_by_marathi
        else -> R.string.sort_by
    }
    val less = when (language) {
        ENGLISH -> R.string.less
        MARATHI -> R.string.less_marathi
        else -> R.string.less
    }
    val more = when (language) {
        ENGLISH -> R.string.more
        MARATHI -> R.string.more_marathi
        else -> R.string.more
    }
    val yes = when (language) {
        ENGLISH -> R.string.yes
        MARATHI -> R.string.yes_marathi
        else -> R.string.yes
    }
    val no = when (language) {
        ENGLISH -> R.string.no
        MARATHI -> R.string.no_marathi
        else -> R.string.no
    }

    //
    val amountTextField = when (language) {
        ENGLISH -> R.string.amount_text_field
        MARATHI -> R.string.amount_text_field_marathi
        else -> R.string.amount_text_field
    }
    val categoryTextField = when (language) {
        ENGLISH -> R.string.category_text_field
        MARATHI -> R.string.category_text_field_marathi
        else -> R.string.category_text_field
    }
    val otherCategoryTextField = when (language) {
        ENGLISH -> R.string.other_category_text_field
        MARATHI -> R.string.other_category_text_field_marathi
        else -> R.string.other_category_text_field
    }
    val subCategoryTextField = when (language) {
        ENGLISH -> R.string.sub_category_text_field
        MARATHI -> R.string.sub_category_text_field_marathi
        else -> R.string.sub_category_text_field
    }
    val otherSubCategoryTextField = when (language) {
        ENGLISH -> R.string.other_sub_category_text_field
        MARATHI -> R.string.other_sub_category_text_field_marathi
        else -> R.string.other_sub_category_text_field
    }
    val dateTextField = when (language) {
        ENGLISH -> R.string.date_text_field
        MARATHI -> R.string.date_text_field_marathi
        else -> R.string.date_text_field
    }
    val extraInfoTextField = when (language) {
        ENGLISH -> R.string.extra_info_text_field
        MARATHI -> R.string.extra_info_text_field_marathi
        else -> R.string.extra_info_text_field
    }
    val placeInfoTextField = when (language) {
        ENGLISH -> R.string.place_info_text_field
        MARATHI -> R.string.place_info_text_field_marathi
        else -> R.string.place_info_text_field
    }

    //
    val search = when (language) {
        ENGLISH -> R.string.search
        MARATHI -> R.string.search_marathi
        else -> R.string.search
    }
    val next = when (language) {
        ENGLISH -> R.string.next
        MARATHI -> R.string.next_marathi
        else -> R.string.next
    }
    val searchPlaceholder = when (language) {
        ENGLISH -> R.string.search_placeholder
        MARATHI -> R.string.search_placeholder_marathi
        else -> R.string.search_placeholder
    }
    val finish = when (language) {
        ENGLISH -> R.string.finish
        MARATHI -> R.string.finish_marathi
        else -> R.string.finish
    }
    val payment = when (language) {
        ENGLISH -> R.string.payment
        MARATHI -> R.string.payment_marathi
        else -> R.string.payment
    }

    val month = when (language) {
        ENGLISH -> R.string.month
        MARATHI -> R.string.month_marathi
        else -> R.string.month
    }
    val quarter = when (language) {
        ENGLISH -> R.string.quarter
        MARATHI -> R.string.quarter_marathi
        else -> R.string.quarter
    }


}