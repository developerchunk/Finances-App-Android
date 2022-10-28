package com.developerstring.financesapp.navigation.navgraph

import com.developerstring.financesapp.util.Constants.DETAIL_TRANSACTION_KEY

sealed class NavRoute(val route: String) {
    object AddTransactionScreen : NavRoute("add_transaction_screen")
    object ViewHistoryScreen : NavRoute("view_history_screen")
    object TransactionDetailsScreen : NavRoute("transaction_details_screen")
    object EditProfileScreen : NavRoute("edit_profile_screen")
//    {
//            fun passId(id: Int): String {
//                return "transaction_details_screen/{$DETAIL_TRANSACTION_KEY}"
//            }
//        }
}
