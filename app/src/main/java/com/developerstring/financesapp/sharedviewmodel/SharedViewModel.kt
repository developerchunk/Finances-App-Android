package com.developerstring.financesapp.sharedviewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.roomdatabase.repository.TransactionRepository
import com.developerstring.financesapp.util.Constants.SAVINGS
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.FilterTransactionState
import com.developerstring.financesapp.util.RequestState
import com.developerstring.financesapp.util.SearchBarState
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

    var id: MutableState<Int> = mutableStateOf(0)

    val transactionAction: MutableState<TransactionAction> =
        mutableStateOf(TransactionAction.NO_ACTION)
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

    val searchBarState: MutableState<SearchBarState> = mutableStateOf(SearchBarState.DELETE)

    val searchBarText: MutableState<String> = mutableStateOf("")

    val filterState: MutableState<FilterTransactionState> =
        mutableStateOf(FilterTransactionState.CLOSED)


    private var _searchedTransactions =
        MutableStateFlow<RequestState<List<TransactionModel>>>(RequestState.Idle)
    val searchedTransactions: StateFlow<RequestState<List<TransactionModel>>> =
        _searchedTransactions

    fun getSearchedTransactions(searchQuery: String) {
        _searchedTransactions.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchAllTransaction(searchQuery = searchQuery).collect {
                    _searchedTransactions.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _searchedTransactions.value = RequestState.Error(e)
        }

        searchBarState.value = SearchBarState.TRIGGERED
    }

    private var _filterSearchedTransactions =
        MutableStateFlow<RequestState<List<TransactionModel>>>(RequestState.Idle)
    val filterSearchedTransactions: StateFlow<RequestState<List<TransactionModel>>> =
        _filterSearchedTransactions

    fun getFilterSearchedTransactions(searchQuery: String, filterQuery: String) {
        _filterSearchedTransactions.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.filterSearchTransaction(
                    searchQuery = searchQuery,
                    filterQuery = filterQuery
                ).collect {
                    _filterSearchedTransactions.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _filterSearchedTransactions.value = RequestState.Error(e)
        }

        searchBarState.value = SearchBarState.TRIGGERED
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

    private var _monthSpent =
        MutableStateFlow<List<Int>>(emptyList())
    val monthSpent: StateFlow<List<Int>> = _monthSpent
    fun searchMonthSpent(
        month: String,
        year: String
    ) {
        viewModelScope.launch {
            repository.searchMonthPayment(month = month, year = year, transaction_type = SPENT)
                .collect {
                    _monthSpent.value = it
                }
        }
    }

    private var _monthAddFund =
        MutableStateFlow<List<Int>>(emptyList())
    val monthAddFund: StateFlow<List<Int>> = _monthAddFund
    fun searchMonthAddFund(
        month: String,
        year: String
    ) {
        viewModelScope.launch {
            repository.searchMonthPayment(month = month, year = year, transaction_type = SPENT)
                .collect {
                    _monthAddFund.value = it
                }
        }
    }

    private var _monthSavings =
        MutableStateFlow<List<Int>>(emptyList())
    val monthSavings: StateFlow<List<Int>> = _monthSavings
    fun searchMonthSavings(
        month: String,
        year: String
    ) {
        viewModelScope.launch {
            repository.searchMonthPayment(month = month, year = year, transaction_type = SAVINGS)
                .collect {
                    _monthSavings.value = it
                }
        }
    }

    private var _dayPayment1 =
        MutableStateFlow<List<Int>>(emptyList())
    val dayPayment1: StateFlow<List<Int>> = _dayPayment1

    // day 2
    private var _dayPayment2 =
        MutableStateFlow<List<Int>>(emptyList())
    val dayPayment2: StateFlow<List<Int>> = _dayPayment2

    // day 3
    private var _dayPayment3 =
        MutableStateFlow<List<Int>>(emptyList())
    val dayPayment3: StateFlow<List<Int>> = _dayPayment3

    // day 4
    private var _dayPayment4 =
        MutableStateFlow<List<Int>>(emptyList())
    val dayPayment4: StateFlow<List<Int>> = _dayPayment4

    // day 5
    private var _dayPayment5 =
        MutableStateFlow<List<Int>>(emptyList())
    val dayPayment5: StateFlow<List<Int>> = _dayPayment5

    // day 6
    private var _dayPayment6 =
        MutableStateFlow<List<Int>>(emptyList())
    val dayPayment6: StateFlow<List<Int>> = _dayPayment6

    // day 7
    private var _dayPayment7 =
        MutableStateFlow<List<Int>>(emptyList())
    val dayPayment7: StateFlow<List<Int>> = _dayPayment7
    fun searchDayPayment(
        day: String,
        month: String,
        year: String,
        transaction_type: String,
        day_no: Int
    ) {
        viewModelScope.launch {
            repository.searchDayPayment(
                day = day,
                month = month,
                year = year,
                transaction_type = transaction_type
            ).collect {
                when (day_no) {
                    0 -> _dayPayment1.value = it
                    1 -> _dayPayment2.value = it
                    2 -> _dayPayment3.value = it
                    3 -> _dayPayment4.value = it
                    4 -> _dayPayment5.value = it
                    5 -> _dayPayment6.value = it
                    6 -> _dayPayment7.value = it
                }
            }
        }
    }

    private var _dayPayment =
        MutableStateFlow<ArrayList<Int>>(arrayListOf<Int>())
    val dayPayment: StateFlow<ArrayList<Int>> = _dayPayment

    fun searchMonthPayment(
        day: String,
        month: String,
        year: String,
        transaction_type: String,
        day_no: Int
    ) {
        viewModelScope.launch {
            repository.searchDayPayment(
                day = day,
                month = month,
                year = year,
                transaction_type = transaction_type
            ).collect {
                _dayPayment.value.set(index = day_no, element = it.sum())
            }
        }
    }

    fun setDayPaymentArray() {
        for (i in 0..30) {
            _dayPayment.value.add(0)
        }
    }

}