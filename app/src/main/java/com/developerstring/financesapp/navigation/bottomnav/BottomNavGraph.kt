package com.developerstring.financesapp.navigation.bottomnav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.developerstring.financesapp.navigation.Graph
import com.developerstring.financesapp.navigation.navgraph.navGraph
import com.developerstring.financesapp.screen.navscreens.ActivityScreen
import com.developerstring.financesapp.screen.navscreens.HomeScreen
import com.developerstring.financesapp.screen.navscreens.ProfileScreen
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel
) {

    NavHost(
        navController = navController,
        route = Graph.BOTTOM_NAV_GRAPH,
        startDestination = BottomNavRoute.Home.route
    ) {
        navGraph(
            navController = navController,
            profileViewModel = profileViewModel,
            sharedViewModel = sharedViewModel
        )
        // MainSetUpNavRoute
        composable(route = BottomNavRoute.Home.route) {
            HomeScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel
            )
        }
        composable(route = BottomNavRoute.Activity.route) {
            ActivityScreen(
                profileViewModel = profileViewModel,
                sharedViewModel = sharedViewModel,
                navController = navController
            )
        }
        composable(route = BottomNavRoute.Profile.route) {
            ProfileScreen(
                profileViewModel = profileViewModel,
                sharedViewModel = sharedViewModel,
                navController = navController
            )
        }
    }

}