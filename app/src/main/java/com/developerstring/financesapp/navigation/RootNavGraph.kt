package com.developerstring.financesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.developerstring.financesapp.navigation.navgraph.navGraph
import com.developerstring.financesapp.navigation.setupnav.onStartNavGraph
import com.developerstring.financesapp.screen.MainScreen
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

@Composable
fun RootNavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    NavHost(
        navController = navController,
        route = Graph.ROOT_NAV_GRAPH,
        startDestination = Graph.ON_START_NAV_GRAPH
    ) {
        onStartNavGraph(navController = navController, sharedViewModel = sharedViewModel)
        composable(route = Graph.BOTTOM_NAV_GRAPH ) {
            MainScreen(sharedViewModel = sharedViewModel)
        }
    }

}

object Graph {
    const val ROOT_NAV_GRAPH = "set_up_nav_graph"
    const val ON_START_NAV_GRAPH = "on_start_nav_graph"
    const val BOTTOM_NAV_GRAPH = "bottom_nav_graph"
    const val NavGraph = "nav_graph"
}