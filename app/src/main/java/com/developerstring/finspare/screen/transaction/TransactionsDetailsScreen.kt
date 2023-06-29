package com.developerstring.finspare.screen.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.roomdatabase.models.TransactionModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.theme.EXTRA_LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants.ADD_FUND
import com.developerstring.finspare.util.Constants.SAVINGS
import com.developerstring.finspare.util.Constants.SPENT
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.TransactionAction
import com.developerstring.finspare.util.calculateContactAmountOld
import com.developerstring.finspare.util.state.AddTransactionMenu
import com.developerstring.finspare.util.state.ProfileAmountType
import com.developerstring.finspare.util.stringToProfileAmountType

@Composable
fun TransactionDetailsScreen(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    profileViewModel: ProfileViewModel,
    publicSharedViewModel: PublicSharedViewModel,
) {

    var categoriesExpanded by rememberSaveable { mutableStateOf(false) }
    var subCategoriesExpanded by rememberSaveable { mutableStateOf(false) }
    var transactionModeExpanded by rememberSaveable { mutableStateOf(false) }

    val id by sharedViewModel.id

    profileViewModel.getTime24Hours()

    val totalAmount by profileViewModel.profileTotalAmount.collectAsState()
    val time24Hours by profileViewModel.profileTime24Hours.collectAsState()

    val getTransactionModel by sharedViewModel.selectTransaction
    val categoryModel by profileViewModel.allCategories.collectAsState()
    val profileModel by profileViewModel.allProfiles.collectAsState()

    var transactionModel by remember {
        mutableStateOf(TransactionModel())
    }

    val language by profileViewModel.profileLanguage.collectAsState()
    val languageText = LanguageText(language = language)

    var oldAmount by remember {
        mutableStateOf(0)
    }

    try {
        oldAmount = getTransactionModel.amount
    } catch (e: Exception) {
//        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }

    var oldTransactionType by remember {
        mutableStateOf<String?>("")
    }
    try {
        oldTransactionType = getTransactionModel.transaction_type
    } catch (_: Exception) {

    }

    if (getTransactionModel.profile_id.isNotEmpty()) {
        profileViewModel.getContactDetails(getTransactionModel.profile_id.toInt())
    }

    val contact by profileViewModel.selectedContact.collectAsState()

    val context = LocalContext.current

    var contactAmount by rememberSaveable {
        mutableStateOf(0)
    }

    var contactAmountType by rememberSaveable {
        mutableStateOf(ProfileAmountType.MONEY_TAKEN)
    }

    var oldContactAmountType by rememberSaveable {
        mutableStateOf(ProfileAmountType.MONEY_TAKEN)
    }

    try {
        oldContactAmountType = getTransactionModel.amount_type.stringToProfileAmountType()
    } catch (_: Exception) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
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
                text = stringResource(id = languageText.payment),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = textColorBW
            )

            IconButton(onClick = {
                profileViewModel.saveTotalAmount(
                    amount = settleDeleteTransaction(
                        transactionType = if (getTransactionModel.amount_type.isNotEmpty()) {
                            when (getTransactionModel.amount_type) {
                                ProfileAmountType.MONEY_TAKEN.name -> ADD_FUND
                                ProfileAmountType.MONEY_GIVEN.name -> SPENT
                                else -> SPENT
                            }
                        } else getTransactionModel.transaction_type,
                        totalAmount = totalAmount,
                        oldAmount = oldAmount
                    )
                )
                if (getTransactionModel.amount_type.isNotEmpty()) {
                    profileViewModel.updateContactAmount(
                        profileId = getTransactionModel.profile_id.toInt(),
                        amount = getTransactionModel.amount,
                        amountType = when (getTransactionModel.amount_type.stringToProfileAmountType()) {
                            ProfileAmountType.MONEY_TAKEN -> ProfileAmountType.MONEY_GIVEN
                            ProfileAmountType.MONEY_GIVEN -> ProfileAmountType.MONEY_TAKEN
                        },
                        context = context
                    )
                }
                sharedViewModel.transactionModel.value = getTransactionModel
                sharedViewModel.transactionAction(
                    action = TransactionAction.DELETE,
                    id = getTransactionModel.id
                )
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
            TransactionContent(
                modifier = Modifier,
                categoryModel = categoryModel,
                profileModel = profileModel,
                transactionModel = getTransactionModel,
                publicSharedViewModel = publicSharedViewModel,
                navController = navController,
                onSaveClicked = {
//                    Toast.makeText(context, id.toString(), Toast.LENGTH_SHORT).show()
                    transactionModel = it.copy(id = id)
                    sharedViewModel.updateTransaction(transactionModel = transactionModel)
                    profileViewModel.getProfileAmount()
                    profileViewModel.saveTotalAmount(
                        amount = settleTransactionAmount(
                            totalAmount = totalAmount,
                            oldAmount = oldAmount,
                            newAmount = transactionModel.amount,
                            oldTransactionType = oldTransactionType!!,
                            newTransactionType = if (getTransactionModel.amount_type == "") {
                                transactionModel.transaction_type
                            } else {
                                when (it.amount_type) {
                                    ProfileAmountType.MONEY_TAKEN.name -> ADD_FUND
                                    ProfileAmountType.MONEY_GIVEN.name -> SPENT
                                    else -> {
                                        SPENT
                                    }
                                }
                            }
                        )
                    )

                    if (it.amount_type.isNotEmpty()) {
                        calculateContactAmountOld(
                            profileAmount = contact.total_amount,
                            oldAmount = oldAmount,
                            newAmount = it.amount,
                            profileAmountType = contact.amount_type.stringToProfileAmountType(),
                            oldAmountType = oldContactAmountType,
                            newAmountType = it.amount_type.stringToProfileAmountType(),
                            values = { newContactValues ->
                                contactAmount = newContactValues.first
                                contactAmountType = newContactValues.second
                            }
                        )

                        profileViewModel.saveContactAmount(
                            profileId = contact.id,
                            amount = contactAmount,
                            amountType = contactAmountType.name,
                            context = context
                        )
                    }

                    navController.popBackStack()
                },
                time24Hours = time24Hours,
                profileViewModel = profileViewModel,
                languageText = languageText,
                transactionModeExpanded = transactionModeExpanded,
                categoriesExpanded = categoriesExpanded,
                subCategoriesExpanded = subCategoriesExpanded,
                onTransactionModeChange = { transactionModeExpanded = it },
                onCategoryChange = { categoriesExpanded = it },
                onSubCategoryChange = { subCategoriesExpanded = it },
                transactionMenu = if (getTransactionModel.amount_type == "") AddTransactionMenu.Regular else AddTransactionMenu.Lend
            )
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