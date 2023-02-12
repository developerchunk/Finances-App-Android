package com.developerstring.finspare.navigation.bottomnav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.developerstring.finspare.navigation.Graph
import com.developerstring.finspare.navigation.navgraph.navGraph
import com.developerstring.finspare.screen.navscreens.ActivityScreen
import com.developerstring.finspare.screen.navscreens.HomeScreen
import com.developerstring.finspare.screen.navscreens.ProfileScreen
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel,
    publicSharedViewModel: PublicSharedViewModel
) {

    NavHost(
        navController = navController,
        route = Graph.BOTTOM_NAV_GRAPH,
        startDestination = BottomNavRoute.Home.route
    ) {
        navGraph(
            navController = navController,
            profileViewModel = profileViewModel,
            sharedViewModel = sharedViewModel,
            publicSharedViewModel = publicSharedViewModel
        )
        // MainSetUpNavRoute
        composable(route = BottomNavRoute.Home.route) {
            HomeScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel,
                publicSharedViewModel = publicSharedViewModel
            )
        }
        composable(route = BottomNavRoute.Activity.route) {
            ActivityScreen(
                profileViewModel = profileViewModel,
                navController = navController
            )
        }
        composable(route = BottomNavRoute.Profile.route) {
            ProfileScreen(
                profileViewModel = profileViewModel,
                navController = navController
            )
        }
    }

}