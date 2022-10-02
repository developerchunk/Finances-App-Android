package com.developerstring.financesapp.roomdatabase.repository

import com.developerstring.financesapp.roomdatabase.dao.TransactionDao
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.util.Constants.SPENT
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {

    val getAllTransactions: Flow<List<TransactionModel>> = transactionDao.getAllTransactions()

    fun getSelectedTransaction(transactionID: Int): Flow<TransactionModel> {
        return transactionDao.getSelectedTransaction(transactionID = transactionID)
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

    fun searchMonthPayment(month: String,year: String, transaction_type: String): Flow<List<Int>> {
        return transactionDao.searchMonthPayment(month = month, year = year, transaction_type = transaction_type)
    }

//    fun searchMonthSavings(month: String): Flow<List<Int>> {
//        return transactionDao.searchMonthPayment(month = month, transaction_type = SAVINGS)
//    }

}