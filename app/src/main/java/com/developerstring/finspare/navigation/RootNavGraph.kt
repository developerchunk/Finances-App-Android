package com.developerstring.finspare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.developerstring.finspare.navigation.setupnav.onStartNavGraph
import com.developerstring.finspare.screen.MainScreen
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel

@Composable
fun RootNavGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel,
    publicSharedViewModel: PublicSharedViewModel
) {

    NavHost(
        navController = navController,
        route = Graph.ROOT_NAV_GRAPH,
        startDestination = Graph.ON_START_NAV_GRAPH
    ) {
        onStartNavGraph(
            navController = navController,
            profileViewModel = profileViewModel,
            sharedViewModel = sharedViewModel,
            publicSharedViewModel = publicSharedViewModel
        )
        composable(route = Graph.BOTTOM_NAV_GRAPH) {
            MainScreen(
                profileViewModel = profileViewModel,
                sharedViewModel = sharedViewModel,
                publicSharedViewModel = publicSharedViewModel
            )
        }
    }

}

object Graph {
    const val ROOT_NAV_GRAPH = "set_up_nav_graph"
    const val ON_START_NAV_GRAPH = "on_start_nav_graph"
    const val BOTTOM_NAV_GRAPH = "bottom_nav_graph"
    const val NavGraph = "nav_graph"
}