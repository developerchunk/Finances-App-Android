package com.developerstring.financesapp.navigation.navgraph

import android.util.Log
import android.widget.Toast
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.developerstring.financesapp.navigation.Graph
import com.developerstring.financesapp.screen.navscreens.profile.EditProfileScreen
import com.developerstring.financesapp.screen.transaction.AddTransaction
import com.developerstring.financesapp.screen.transaction.TransactionDetailsScreen
import com.developerstring.financesapp.screen.transaction.ViewHistoryScreen
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.util.Constants.DETAIL_TRANSACTION_KEY
import kotlin.reflect.typeOf

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
    }

}