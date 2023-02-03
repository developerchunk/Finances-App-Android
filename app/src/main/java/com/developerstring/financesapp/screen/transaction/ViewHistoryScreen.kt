package com.developerstring.financesapp.screen.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.developerstring.financesapp.navigation.navgraph.NavRoute
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.TopAppBarHistory
import com.developerstring.financesapp.ui.components.TransactionsItemView
import com.developerstring.financesapp.ui.theme.backgroundColor
import com.developerstring.financesapp.util.Constants.oldFirstFilter
import com.developerstring.financesapp.util.state.FilterTransactionState
import com.developerstring.financesapp.util.state.RequestState
import com.developerstring.financesapp.util.state.SearchBarState

@Composable
fun ViewHistoryScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    oldFirstFilter.value = false

    profileViewModel.getAllCategories()

    val transactionAction = sharedViewModel.transactionAction
    sharedViewModel.transactionAction(action = transactionAction.value)

    val searchBarState: SearchBarState by sharedViewModel.searchBarState
    val filterState: FilterTransactionState by sharedViewModel.filterState
    val searchBarText: String by sharedViewModel.searchBarText

    val allTransactions by sharedViewModel.allTransactions.collectAsState()
    val searchedTransactions by sharedViewModel.searchedTransactions.collectAsState()
    val filterSearchedTransactions by sharedViewModel.filterSearchedTransactions.collectAsState()

    val currency by profileViewModel.profileCurrency.collectAsState()

    val time24Hours by profileViewModel.profileTime24Hours.collectAsState()

    val categories by profileViewModel.allCategories.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarHistory(
                sharedViewModel = sharedViewModel,
                navController = navController,
                searchBarState = searchBarState,
                searchBarText = searchBarText,
                categoriesModels = categories
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(backgroundColor)
        ) {
            TransactionHistoryContain(
                allTransactions = allTransactions,
                searchTransactions = searchedTransactions,
                filterSearchTransactions = filterSearchedTransactions,
                searchBarState = searchBarState,
                filterState = filterState,
                currency = currency,
                time24Hours = time24Hours,
                onClicked = {transaction ->
                    sharedViewModel.id.value = transaction.id
                    sharedViewModel.selectTransaction.value = transaction
                    navController.navigate(route = NavRoute.TransactionDetailsScreen.route)
                }
            )
        }


    }

}

@Composable
fun TransactionHistoryContain(
    allTransactions: RequestState<List<TransactionModel>>,
    searchTransactions: RequestState<List<TransactionModel>>,
    filterSearchTransactions: RequestState<List<TransactionModel>>,
    searchBarState: SearchBarState,
    filterState: FilterTransactionState,
    currency: String,
    time24Hours: Boolean,
    onClicked: (TransactionModel) -> Unit
) {

    if (searchBarState == SearchBarState.TRIGGERED && filterState == FilterTransactionState.OPENED) {
        if (filterSearchTransactions is RequestState.Success) {
            HistoryTransactionContent(
                allTransactions = filterSearchTransactions.data,
                currency = currency,
                time24Hours = time24Hours,
                onClicked = {transaction ->
                    onClicked(transaction)
                }
            )
        }
    } else if (searchBarState == SearchBarState.TRIGGERED) {
        if (searchTransactions is RequestState.Success) {
            HistoryTransactionContent(
                allTransactions = searchTransactions.data,
                currency = currency,
                time24Hours = time24Hours,
                onClicked = {transaction ->
                    onClicked(transaction)
                }
            )
        }
    } else if (filterState == FilterTransactionState.OPENED) {
        if (searchTransactions is RequestState.Success) {
            HistoryTransactionContent(
                allTransactions = searchTransactions.data,
                currency = currency,
                time24Hours = time24Hours,
                onClicked = {transaction ->
                    onClicked(transaction)
                }
            )
        }
    } else {
        if (allTransactions is RequestState.Success) {
            HistoryTransactionContent(
                allTransactions = allTransactions.data,
                currency = currency,
                time24Hours = time24Hours,
                onClicked = {transaction ->
                    onClicked(transaction)
                }
            )
        }
    }


}

@Composable
fun HistoryTransactionContent(
    allTransactions: List<TransactionModel>,
    currency: String,
    time24Hours: Boolean,
    onClicked: (TransactionModel) -> Unit
) {



    LazyColumn {
        items(
            items =
            if (oldFirstFilter.value) {
                allTransactions.reversed()
            } else {
                allTransactions
            }
        ) {
            TransactionsItemView(
                transactionModel = it,
                currency = currency,
                navigateToDetails = { transaction ->
                    onClicked(transaction)
                },
                time24Hours = time24Hours
            )
        }
    }
}