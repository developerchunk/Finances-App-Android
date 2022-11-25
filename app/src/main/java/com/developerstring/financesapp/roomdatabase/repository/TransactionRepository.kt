package com.developerstring.financesapp.roomdatabase.repository

import com.developerstring.financesapp.roomdatabase.dao.TransactionDao
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {

    val getAllTransactions: Flow<List<TransactionModel>> = transactionDao.getAllTransactions()

    fun getSelectedTransaction(transactionID: Int): Flow<TransactionModel> {
        return transactionDao.getSelectedTransaction(transactionID = transactionID)
    }

    fun getLastTransaction(): Flow<Int> {
        return transactionDao.getLastTransaction()
    }

    suspend fun addTransaction(transactionModel: TransactionModel) {
        transactionDao.addTransaction(transactionModel = transactionModel)
    }

    suspend fun updateTransaction(transactionModel: TransactionModel) {
        transactionDao.updateTransaction(transactionModel = transactionModel)
    }

    suspend fun deleteTransaction(transactionModel: TransactionModel) {
        transactionDao.deleteTransaction(transactionModel = transactionModel)
    }

    suspend fun deleteAllTransaction() {
        transactionDao.deleteAllTransactions()
    }

    fun searchAllTransaction(searchQuery: String): Flow<List<TransactionModel>> {
        return transactionDao.searchAllTransactions(searchQuery = searchQuery)
    }

    fun filterSearchTransaction(searchQuery: String, filterQuery: String): Flow<List<TransactionModel>> {
        return transactionDao.filterSearchTransactions(searchQuery = searchQuery, filterQuery = filterQuery)
    }

    fun searchMonthPayment(month: String, year: String, transaction_type: String): Flow<List<Int>> {
        return transactionDao.searchMonthPayment(
            month = month,
            year = year,
            transaction_type = transaction_type
        )
    }

    fun searchDayPayment(
        day: String,
        month: String,
        year: String,
        transaction_type: String
    ): Flow<List<Int>> {
        return transactionDao.searchDayPayment(
            day = day,
            month = month,
            year = year,
            transaction_type = transaction_type
        )
    }

}