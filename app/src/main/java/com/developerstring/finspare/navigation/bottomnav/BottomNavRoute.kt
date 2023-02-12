package com.developerstring.finspare.navigation.bottomnav

import com.developerstring.finspare.R
import com.developerstring.finspare.util.Constants.ACTIVITY_ROUTE
import com.developerstring.finspare.util.Constants.HOME_ROUTE
import com.developerstring.finspare.util.Constants.PROFILE_ROUTE

sealed class BottomNavRoute(
    val route: String,
    val title: Int,
    val icon: Int,
    val icon_focused: Int,
) {
    // for home
    object Home : BottomNavRoute(
        route = HOME_ROUTE,
        title = R.string.home_bottom_nav,
        icon = R.drawable.ic_bottom_home,
        icon_focused = R.drawable.ic_bottom_home_focused
    )

    // for report
    object Activity : BottomNavRoute(
        route = ACTIVITY_ROUTE,
        title = R.string.activity_bottom_nav,
        icon = R.drawable.ic_bottom_activity,
        icon_focused = R.drawable.ic_bottom_activity_focused
    )

    // for report
    object Profile : BottomNavRoute(
        route = PROFILE_ROUTE,
        title = R.string.profile_bottom_nav,
        icon = R.drawable.ic_bottom_profile,
        icon_focused = R.drawable.ic_bottom_profile_focused
    )
}
