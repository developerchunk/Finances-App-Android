package com.developerstring.financesapp.sharedviewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.roomdatabase.repository.TransactionRepository
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
        MutableStateFlow<List<TransactionModel>>(emptyList())
    val allTransactions: StateFlow<List<TransactionModel>> = _allTransactions

    private var _monthSpent =
        MutableStateFlow<List<Int>>(emptyList())
    val monthSpent: StateFlow<List<Int>> = _monthSpent

    private var _monthSavings =
        MutableStateFlow<List<Int>>(emptyList())
    val monthSavings: StateFlow<List<Int>> = _monthSavings

    val id: MutableState<Int> = mutableStateOf(0)

    fun getAllTransactions() {
        viewModelScope.launch {
            repository.getAllTransactions.collect {
                _allTransactions.value = it
            }
        }
    }

    fun addTask(
        transactionModel: TransactionModel
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(transactionModel = transactionModel)
        }
    }

    fun searchMonthSpent(
        month: String
    ) {
        viewModelScope.launch {
            repository.searchMonthSpent(month = month).collect {
                _monthSpent.value = it
            }
        }
    }

    fun searchMonthSavings(
        month: String
    ) {
        viewModelScope.launch {
            repository.searchMonthSavings(month = month).collect {
                _monthSavings.value = it
            }
        }
    }

}