package com.developerstring.financesapp.screen.transaction

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel

@Composable
fun AddTransaction(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel
) {

    sharedViewModel.getAllTransactions()
    val text by sharedViewModel.allTransactions.collectAsState()

    Text(text = text.toString())

}