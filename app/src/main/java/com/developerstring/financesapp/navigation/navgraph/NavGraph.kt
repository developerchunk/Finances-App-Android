package com.developerstring.financesapp.navigation.navgraph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.developerstring.financesapp.navigation.Graph
import com.developerstring.financesapp.screen.charts.ActivityChartScreen
import com.developerstring.financesapp.screen.navscreens.profile.EditProfileScreen
import com.developerstring.financesapp.screen.transaction.AddTransaction
import com.developerstring.financesapp.screen.transaction.TransactionDetailsScreen
import com.developerstring.financesapp.screen.transaction.ViewHistoryScreen
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.PublicSharedViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

fun NavGraphBuilder.navGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel,
    publicSharedViewModel: PublicSharedViewModel
) {

    navigation(
        route = Graph.NavGraph,
        startDestination = NavRoute.AddTransactionScreen.route
    ) {
        composable(NavRoute.AddTransactionScreen.route) {
            AddTransaction(
                navController = navController,
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel,
                publicSharedViewModel = publicSharedViewModel
            )
        }

        composable(route = NavRoute.ViewHistoryScreen.route) {
            ViewHistoryScreen(
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel,
                navController = navController
            )
        }

        composable(
            route = NavRoute.TransactionDetailsScreen.route
        ) {
            TransactionDetailsScreen(
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel,
                navController = navController,
                publicSharedViewModel = publicSharedViewModel
//                id = task.arguments?.getInt(DETAIL_TRANSACTION_KEY)!!.toInt()
            )
        }
        composable(
            route = NavRoute.EditProfileScreen.route
        ) {
            EditProfileScreen(
                profileViewModel = profileViewModel,
                navController = navController,
//                id = task.arguments?.getInt(DETAIL_TRANSACTION_KEY)!!.toInt()
            )
        }
        composable(
            route = NavRoute.ActivityChartScreen.route
        ) {
            ActivityChartScreen(
                sharedViewModel = sharedViewModel,
                publicSharedViewModel = publicSharedViewModel,
                navController = navController,
//                id = task.arguments?.getInt(DETAIL_TRANSACTION_KEY)!!.toInt()
            )
        }
    }

}