package com.developerstring.financesapp.screen.transaction

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.ViewTransactionItem

@Composable
fun ViewHistoryScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel
) {


    val allTransactions by sharedViewModel.allTransactions.collectAsState()
    val currency by profileViewModel.profileCurrency.collectAsState()

    LazyColumn {
        items(
            items = allTransactions
        ) {
            ViewTransactionItem(
                date = it.date,
                category = it.category,
                amount = it.amount.toString(),
                transactionType = it.transaction_type,
                currency = currency
            )
        }
    }

}