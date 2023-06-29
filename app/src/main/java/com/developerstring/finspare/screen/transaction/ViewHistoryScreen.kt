package com.developerstring.finspare.screen.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.roomdatabase.models.TransactionModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.components.NoDataAvailable
import com.developerstring.finspare.ui.components.TopAppBarHistory
import com.developerstring.finspare.ui.components.TransactionsItemView
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.Constants.oldFirstFilter
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.ProfileListReturn
import com.developerstring.finspare.util.refineProfileModel
import com.developerstring.finspare.util.state.FilterTransactionState
import com.developerstring.finspare.util.state.RequestState
import com.developerstring.finspare.util.state.SearchBarState

@Composable
fun ViewHistoryScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
) {

    oldFirstFilter.value = false

    profileViewModel.getAllCategories()
    profileViewModel.getAllProfiles()
    profileViewModel.getAllProfiles()

    val transactionAction = sharedViewModel.transactionAction
    sharedViewModel.transactionAction(action = transactionAction.value)

    val searchBarState: SearchBarState by sharedViewModel.searchBarState
    val filterState: FilterTransactionState by sharedViewModel.filterState
    val searchBarText: String by sharedViewModel.searchBarText

    val allTransactions by sharedViewModel.allTransactions.collectAsState()
    val searchedTransactions by sharedViewModel.searchedTransactions.collectAsState()
    val filterSearchedTransactions by sharedViewModel.filterSearchedTransactions.collectAsState()
    val allProfiles by profileViewModel.allProfiles.collectAsState()

    val currency by profileViewModel.profileCurrency.collectAsState()

    val time24Hours by profileViewModel.profileTime24Hours.collectAsState()

    val categories by profileViewModel.allCategories.collectAsState()

    val languageText = LanguageText(Constants.LANGUAGE)

    var noData by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBarHistory(
                sharedViewModel = sharedViewModel,
                navController = navController,
                searchBarState = searchBarState,
                searchBarText = searchBarText,
                categoriesModels = categories,
                allProfiles = allProfiles,
                languageText = languageText,
            )
        }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .background(backgroundColor),
            verticalArrangement = if (noData) Arrangement.Center else Arrangement.Top,
            horizontalAlignment = if (noData) Alignment.CenterHorizontally else Alignment.Start,
        ) {
            TransactionHistoryContain(
                allTransactions = allTransactions,
                searchTransactions = searchedTransactions,
                filterSearchTransactions = filterSearchedTransactions,
                allProfiles = allProfiles,
                searchBarState = searchBarState,
                filterState = filterState,
                currency = currency,
                time24Hours = time24Hours,
                noData = { value ->
                    noData = value
                }
            ) { transaction ->
                sharedViewModel.id.value = transaction.id
                sharedViewModel.selectTransaction.value = transaction
                navController.navigate(route = NavRoute.TransactionDetailsScreen.route)
            }
        }
    }

}

@Composable
fun TransactionHistoryContain(
    allTransactions: RequestState<List<TransactionModel>>,
    searchTransactions: RequestState<List<TransactionModel>>,
    filterSearchTransactions: RequestState<List<TransactionModel>>,
    allProfiles: RequestState<List<ProfileModel>>,
    searchBarState: SearchBarState,
    filterState: FilterTransactionState,
    currency: String,
    time24Hours: Boolean,
    noData: (Boolean) -> Unit,
    onClicked: (TransactionModel) -> Unit
) {

    var profiles = mutableListOf<ProfileModel>()
    ProfileListReturn(
        allProfiles,
        profileList = { profiles = it.refineProfileModel() as MutableList<ProfileModel> })

    if (searchBarState == SearchBarState.TRIGGERED && filterState == FilterTransactionState.OPENED) {
        if (filterSearchTransactions is RequestState.Success) {
            HistoryTransactionContent(
                allTransactions = filterSearchTransactions.data,
                allProfiles = profiles,
                currency = currency,
                time24Hours = time24Hours,
                noData = noData
            ) { transaction ->
                onClicked(transaction)
            }
        }
    } else if (searchBarState == SearchBarState.TRIGGERED) {
        if (searchTransactions is RequestState.Success) {
            HistoryTransactionContent(
                allTransactions = searchTransactions.data,
                allProfiles = profiles,
                currency = currency,
                time24Hours = time24Hours,
                noData = noData
            ) { transaction ->
                onClicked(transaction)
            }
        }
    } else if (filterState == FilterTransactionState.OPENED) {
        if (searchTransactions is RequestState.Success) {
            HistoryTransactionContent(
                allTransactions = searchTransactions.data,
                allProfiles = profiles,
                currency = currency,
                time24Hours = time24Hours,
                noData = noData
            ) { transaction ->
                onClicked(transaction)
            }
        }
    } else {
        if (allTransactions is RequestState.Success) {
            HistoryTransactionContent(
                allTransactions = allTransactions.data,
                allProfiles = profiles,
                currency = currency,
                time24Hours = time24Hours,
                noData = noData
            ) { transaction ->
                onClicked(transaction)
            }
        }
    }


}

@Composable
fun HistoryTransactionContent(
    allTransactions: List<TransactionModel>,
    allProfiles: List<ProfileModel>,
    currency: String,
    time24Hours: Boolean,
    noData: (Boolean) -> Unit,
    onClicked: (TransactionModel) -> Unit
) {

    var expandedLaunched by remember {
        mutableStateOf(false)
    }

    if (allTransactions.isEmpty()) {
        noData(true)
        NoDataAvailable(title = "Add a new payment to \nsee them.")
    } else {
        noData(false)
        LazyColumn {
            items(
                items =
                if (oldFirstFilter.value) {
                    allTransactions.reversed()
                } else {
                    allTransactions
                },

                ) {
                TransactionsItemView(
                    profileName = allProfiles.find { profile -> profile.id.toString() == it.profile_id }?.name
                        ?: it.profile_name,
                    transactionModel = it,
                    currency = currency,
                    navigateToDetails = { transaction ->
                        onClicked(transaction)
                    },
                    time24Hours = time24Hours,
                    launched = true,
                    expandedLaunched = expandedLaunched
                )
            }
        }
    }

    LaunchedEffect(key1 = true) {
        expandedLaunched = true
    }
}