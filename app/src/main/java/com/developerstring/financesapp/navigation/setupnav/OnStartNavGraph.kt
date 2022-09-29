package com.developerstring.financesapp.navigation.setupnav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.developerstring.financesapp.navigation.Graph
import com.developerstring.financesapp.screen.MainScreen
import com.developerstring.financesapp.screen.onstart.*
import com.developerstring.financesapp.screen.profilecreate.CreateProfileScreen
import com.developerstring.financesapp.screen.profilecreate.CreateProfileScreen2
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

fun NavGraphBuilder.onStartNavGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel
) {
    navigation(
        route = Graph.ON_START_NAV_GRAPH,
        startDestination = SetUpNavRoute.SplashSetUpNavRoute.route
    ) {
        // MainSetUpNavRoute
        composable(
            route = SetUpNavRoute.MainSetUpNavRoute.route
        ) {
            MainScreen(profileViewModel = profileViewModel, sharedViewModel = sharedViewModel)
        }

        // Splash SetUpNavRoute
        composable(
            route = SetUpNavRoute.SplashSetUpNavRoute.route
        ) {
            SplashScreen(
                navController = navController,
                profileViewModel = profileViewModel,
                sharedViewModel = sharedViewModel
            )
        }
        // CreateProfileSetUpNavRoute
        composable(
            route = SetUpNavRoute.CreateProfileSetUpNavRoute.route
        ) {
            CreateProfileScreen(navController = navController, profileViewModel = profileViewModel)
        }
        // CreateProfileSetUpNavRoute2
        composable(
            route = SetUpNavRoute.CreateProfileSetUpNavRoute2.route
        ) {
            CreateProfileScreen2(navController = navController, profileViewModel = profileViewModel)
        }

        // Boarding Screens
        // BoardingSetUpNavRoute1
        composable(
            route = SetUpNavRoute.BoardingSetUpNavRoute1.route
        ) {
            BoardingScreen1(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }
        // BoardingSetUpNavRoute2
        composable(
            route = SetUpNavRoute.BoardingSetUpNavRoute2.route
        ) {
            BoardingScreen2(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }
        // BoardingSetUpNavRoute3
        composable(
            route = SetUpNavRoute.BoardingSetUpNavRoute3.route
        ) {
            BoardingScreen3(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }
        // BoardingSetUpNavRoute4
        composable(
            route = SetUpNavRoute.BoardingSetUpNavRoute4.route
        ) {
            BoardingScreen4(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }


    }
}