package com.developerstring.finspare.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.developerstring.finspare.navigation.Graph
import com.developerstring.finspare.navigation.setupnav.onStartNavGraph
import com.developerstring.finspare.screen.charts.ActivityChartScreen
import com.developerstring.finspare.screen.charts.AmountChartScreen
import com.developerstring.finspare.screen.charts.CategoryChartScreen
import com.developerstring.finspare.screen.charts.ProfileAmountChartScreen
import com.developerstring.finspare.screen.navscreens.profile.AboutScreen
import com.developerstring.finspare.screen.navscreens.profile.EditCategoryDetailScreen
import com.developerstring.finspare.screen.navscreens.profile.EditCategoryScreen
import com.developerstring.finspare.screen.navscreens.profile.EditContactsDetailScreen
import com.developerstring.finspare.screen.navscreens.profile.EditContactsScreen
import com.developerstring.finspare.screen.navscreens.profile.EditLanguageScreen
import com.developerstring.finspare.screen.navscreens.profile.EditProfileScreen
import com.developerstring.finspare.screen.navscreens.profile.SettingScreen
import com.developerstring.finspare.screen.navscreens.profile.TermsAndConditionsScreen
import com.developerstring.finspare.screen.transaction.AddTransaction
import com.developerstring.finspare.screen.transaction.TransactionDetailsScreen
import com.developerstring.finspare.screen.transaction.ViewHistoryScreen
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel

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
        onStartNavGraph(
            navController = navController,
            profileViewModel = profileViewModel,
            sharedViewModel = sharedViewModel,
            publicSharedViewModel = publicSharedViewModel
        )
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
                navController = navController,
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
            )
        }
        composable(
            route = NavRoute.EditProfileScreen.route
        ) {
            EditProfileScreen(
                profileViewModel = profileViewModel,
                navController = navController,
            )
        }
        composable(
            route = NavRoute.EditLanguageScreen.route
        ) {
            EditLanguageScreen(
                profileViewModel = profileViewModel,
                navController = navController,
            )
        }
        composable(
            route = NavRoute.EditContactsScreen.route
        ) {
            EditContactsScreen(
                profileViewModel = profileViewModel,
                navController = navController,
            )
        }
        composable(
            route = NavRoute.EditContactsDetailScreen.route
        ) {
            EditContactsDetailScreen(
                profileViewModel = profileViewModel,
                navController = navController,
            )
        }
        composable(
            route = NavRoute.ActivityChartScreen.route
        ) {
            ActivityChartScreen(
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel,
                navController = navController,
            )
        }
        composable(
            route = NavRoute.CategoryChartScreen.route
        ) {
            CategoryChartScreen(
                sharedViewModel = sharedViewModel,
                navController = navController,
                profileViewModel = profileViewModel
            )
        }
        composable(
            route = NavRoute.EditCategoryScreen.route
        ) {
            EditCategoryScreen(
                navController = navController,
                profileViewModel = profileViewModel,
            )
        }
        composable(
            route = NavRoute.EditCategoryDetailScreen.route
        ) {
            EditCategoryDetailScreen(
                navController = navController,
                profileViewModel = profileViewModel,
            )
        }

        composable(
            route = NavRoute.SettingScreen.route
        ) {
            SettingScreen(
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel,
                navController = navController,
            )
        }

        composable(
            route = NavRoute.AboutScreen.route
        ) {
            AboutScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoute.TermsAndConditionsScreen.route
        ) {
            TermsAndConditionsScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoute.AmountChartScreen.route
        ) {
            AmountChartScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                profileViewModel = profileViewModel
            )
        }

        composable(
            route = NavRoute.ProfileAmountChartScreen.route
        ) {
            ProfileAmountChartScreen(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }
    }

}