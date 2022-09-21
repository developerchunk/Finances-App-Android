package com.developerstring.financesapp.navigation.bottomnav

import com.developerstring.financesapp.R

sealed class BottomNavRoute(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int,
) {
    // for home
    object Home: BottomNavRoute(
        route = "home",
        title = "Home",
        icon = R.drawable.ic_bottom_home,
        icon_focused = R.drawable.ic_bottom_home_focused
    )

    // for report
    object Activity: BottomNavRoute(
        route = "activity",
        title = "Activity",
        icon = R.drawable.ic_bottom_activity,
        icon_focused = R.drawable.ic_bottom_activity_focused
    )

    // for report
    object Profile: BottomNavRoute(
        route = "profile",
        title = "Profile",
        icon = R.drawable.ic_bottom_profile,
        icon_focused = R.drawable.ic_bottom_profile_focused
    )
}
