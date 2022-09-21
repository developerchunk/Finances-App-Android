package com.developerstring.financesapp.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.developerstring.financesapp.navigation.Graph
import com.developerstring.financesapp.screen.transaction.AddTransaction
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

fun NavGraphBuilder.navGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel
) {

    navigation(
        route = Graph.NavGraph,
        startDestination = NavRoute.AddTransactionScreen.route
    ) {
        composable(NavRoute.AddTransactionScreen.route) {
            AddTransaction(
                navController = navController,
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel
            )
        }
    }

}