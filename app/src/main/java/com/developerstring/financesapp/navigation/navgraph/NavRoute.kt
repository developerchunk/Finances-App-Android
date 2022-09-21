package com.developerstring.financesapp.navigation.navgraph

sealed class NavRoute(val route: String) {
    object AddTransactionScreen: NavRoute("add_transaction_screen")
}
