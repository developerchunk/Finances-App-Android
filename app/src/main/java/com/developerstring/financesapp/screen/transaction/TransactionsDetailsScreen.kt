package com.developerstring.financesapp.screen.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.PublicSharedViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.ADD_FUND
import com.developerstring.financesapp.util.Constants.SAVINGS
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.TransactionAction

@Composable
fun TransactionDetailsScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    profileViewModel: ProfileViewModel,
    publicSharedViewModel: PublicSharedViewModel
) {

    val context = LocalContext.current

    val id = sharedViewModel.id.value

    val totalAmount by profileViewModel.profileTotalAmount.collectAsState()

    val getTransactionModel by sharedViewModel.selectedTransaction.collectAsState()
    var transactionModel by remember {
        mutableStateOf(TransactionModel())
    }

    val scrollState = rememberScrollState()

//    val oldTransactionModel by mutableStateOf<TransactionModel>(getTransactionModel!!)

    var oldAmount by mutableStateOf(0)
    try {
        oldAmount = getTransactionModel!!.amount
    } catch (e: Exception) {
//        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }

    var oldTransactionType by mutableStateOf<String?>("")
    try {
        oldTransactionType = getTransactionModel!!.transaction_type
    } catch (_: Exception) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(state = scrollState)
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
                    tint = textColorBW
                )
            }

            Text(
                text = stringResource(id = R.string.payment),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = textColorBW
            )

            IconButton(onClick = {
                profileViewModel.saveTotalAmount(
                    context = context,
                    amount = settleDeleteTransaction(
                        transactionType = getTransactionModel!!.transaction_type,
                        totalAmount = totalAmount,
                        oldAmount = oldAmount
                    )
                )
                sharedViewModel.transactionAction(action = TransactionAction.DELETE)
                sharedViewModel.transactionModel.value = getTransactionModel!!
                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "delete",
                    tint = textColorBW
                )
            }

        }

        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .fillMaxSize()
        ) {
            if (getTransactionModel!=null) {
                TransactionContent(
                    modifier = Modifier,
                    transactionModel = getTransactionModel!!,
                    publicSharedViewModel = publicSharedViewModel,
                    onSaveClicked = {
//                    Toast.makeText(context, id.toString(), Toast.LENGTH_SHORT).show()
                        transactionModel = TransactionModel(
                            id = id,
                            amount = it.amount,
                            transaction_type = it.transaction_type,
                            category = it.category,
                            subCategory = it.subCategory,
                            date = it.date,
                            day = it.day,
                            month = it.month,
                            year = it.year,
                            info = it.info,
                            place = it.place,
                            categoryOther = it.categoryOther,
                            subCategoryOther = it.subCategoryOther,
                        )
                        sharedViewModel.updateTransaction(transactionModel = transactionModel)
                        profileViewModel.saveTotalAmount(
                            context = context,
                            amount = settleTransactionAmount(
                                totalAmount = totalAmount,
                                oldAmount = oldAmount,
                                newAmount = transactionModel.amount,
                                oldTransactionType = oldTransactionType!!,
                                newTransactionType = transactionModel.transaction_type
                            )
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

fun settleTransactionAmount(
    totalAmount: Int,
    oldAmount: Int,
    newAmount: Int,
    oldTransactionType: String,
    newTransactionType: String,
): Int {

    fun settleAmount(): Int {
        val differenceAmount = newAmount - oldAmount

        return when (oldTransactionType) {
            SPENT -> (totalAmount - differenceAmount)
            ADD_FUND -> (totalAmount + differenceAmount)
            else -> totalAmount
        }
    }

    fun settleTransactionType(): Int {
        return if (oldTransactionType != newTransactionType) {
            when (newTransactionType) {
                SPENT -> totalAmount - newAmount - oldAmount
                ADD_FUND -> totalAmount + newAmount + oldAmount
                SAVINGS -> if (oldTransactionType == SPENT) {
                    totalAmount + oldAmount
                } else {
                    totalAmount - oldAmount
                }
                else -> totalAmount
            }
        } else {
            settleAmount()
        }
    }

    return settleTransactionType()

}

fun settleDeleteTransaction(
    transactionType: String,
    totalAmount: Int,
    oldAmount: Int
): Int {
    return when (transactionType) {
        SPENT -> totalAmount + oldAmount
        ADD_FUND -> totalAmount - oldAmount
        else -> totalAmount
    }
}