package com.developerstring.financesapp.sharedviewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.roomdatabase.repository.TransactionRepository
import com.developerstring.financesapp.util.Constants.SAVINGS
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.RequestState
import com.developerstring.financesapp.util.TransactionAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private var _allTransactions =
        MutableStateFlow<RequestState<List<TransactionModel>>>(RequestState.Idle)
    val allTransactions: StateFlow<RequestState<List<TransactionModel>>> = _allTransactions

    private var _monthSpent =
        MutableStateFlow<List<Int>>(emptyList())
    val monthSpent: StateFlow<List<Int>> = _monthSpent

    private var _monthSavings =
        MutableStateFlow<List<Int>>(emptyList())
    val monthSavings: StateFlow<List<Int>> = _monthSavings

    var id: MutableState<Int> = mutableStateOf(0)

    val transactionAction: MutableState<TransactionAction> = mutableStateOf(TransactionAction.NO_ACTION)
    val transactionModel: MutableState<TransactionModel> = mutableStateOf(TransactionModel())

    private val _selectedTransaction: MutableStateFlow<TransactionModel?> = MutableStateFlow(null)
    val selectedTransaction: MutableStateFlow<TransactionModel?> = _selectedTransaction

    fun getAllTransactions() {
        _allTransactions.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTransactions.collect {
                    _allTransactions.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTransactions.value = RequestState.Error(e)
        }
    }

    fun getSelectedTransaction(transactionID: Int) {
        viewModelScope.launch {
            repository.getSelectedTransaction(transactionID = transactionID).collect { task ->
                _selectedTransaction.value = task
            }
        }
    }

    fun addTransaction(
        transactionModel: TransactionModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(transactionModel = transactionModel)
        }
    }

    fun updateTransaction(
        transactionModel: TransactionModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTransaction(transactionModel = transactionModel)
        }
    }

    private fun deleteTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(transactionModel = transactionModel.value)
        }
        this.transactionAction.value = TransactionAction.NO_ACTION
    }

    fun transactionAction(
        action: TransactionAction,
    ) {
        when (action) {
            TransactionAction.DELETE -> deleteTransaction()
            else -> {

            }
        }
        this.transactionAction.value = TransactionAction.NO_ACTION
    }

    fun searchMonthSpent(
        month: String,
        year: String
    ) {
        viewModelScope.launch {
            repository.searchMonthPayment(month = month, year = year, transaction_type = SPENT).collect {
                _monthSpent.value = it
            }
        }
    }

    fun searchMonthSavings(
        month: String,
        year: String
    ) {
        viewModelScope.launch {
            repository.searchMonthPayment(month = month, year = year, transaction_type = SAVINGS).collect {
                _monthSavings.value = it
            }
        }
    }

}