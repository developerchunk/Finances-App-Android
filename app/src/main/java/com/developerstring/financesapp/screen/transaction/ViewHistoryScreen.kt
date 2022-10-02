package com.developerstring.financesapp.screen.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.navigation.navgraph.NavRoute
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.ViewTransactionItem
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.RequestState

@Composable
fun ViewHistoryScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val transactionAction = sharedViewModel.transactionAction
    sharedViewModel.transactionAction(action = transactionAction.value)

    val allTransactions by sharedViewModel.allTransactions.collectAsState()

    TransactionHistoryContain(
        sharedViewModel = sharedViewModel,
        profileViewModel = profileViewModel,
        navController = navController,
        allTransactions = allTransactions
    )

}

@Composable
fun TransactionHistoryContain(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    allTransactions: RequestState<List<TransactionModel>>
) {

    val currency by profileViewModel.profileCurrency.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundColor)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close",
                    tint = MaterialTheme.colors.textColorBW
                )
            }

            Text(
                text = stringResource(id = R.string.history),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = MaterialTheme.colors.textColorBW
            )

            IconButton(onClick = {
//                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "close",
                    tint = MaterialTheme.colors.textColorBW
                )
            }

        }

        if (allTransactions is RequestState.Success)
            HistoryTransactionContent(
                allTransactions = allTransactions.data,
                currency = currency,
                sharedViewModel = sharedViewModel,
                navController = navController
            )
    }
}

@Composable
fun HistoryTransactionContent(
    allTransactions: List<TransactionModel>,
    currency: String,
    sharedViewModel: SharedViewModel,
    navController: NavController
) {
    LazyColumn {
        items(
            items = allTransactions
        ) {
            ViewTransactionItem(
                transactionModel = it,
                currency = currency,
                navigateToDetails = { id ->
                    sharedViewModel.id.value = id
                    sharedViewModel.getSelectedTransaction(transactionID = id)
                    navController.navigate(route = NavRoute.TransactionDetailsScreen.route)
                }
            )
        }
    }
}