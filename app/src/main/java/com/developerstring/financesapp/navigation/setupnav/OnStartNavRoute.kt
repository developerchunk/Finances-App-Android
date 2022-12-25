package com.developerstring.financesapp.navigation.setupnav

sealed class SetUpNavRoute(val route: String) {

    object MainSetUpNavRoute: SetUpNavRoute(route = "main_screen")
    object SplashSetUpNavRoute: SetUpNavRoute(route = "splash_screen")
    object LanguageScreenSetUpNavRoute: SetUpNavRoute(route = "language_screen")
    object CreateProfileSetUpNavRoute: SetUpNavRoute(route = "create_profile_screen")
    object CreateProfileSetUpNavRoute2: SetUpNavRoute(route = "create_profile_screen_2")

    // Boarding Screens
    object BoardingSetUpNavRoute1: SetUpNavRoute(route = "on_boarding_screen_1")
    object BoardingSetUpNavRoute2: SetUpNavRoute(route = "on_boarding_screen_2")
    object BoardingSetUpNavRoute3: SetUpNavRoute(route = "on_boarding_screen_3")
    object BoardingSetUpNavRoute4: SetUpNavRoute(route = "on_boarding_screen_4")
}


