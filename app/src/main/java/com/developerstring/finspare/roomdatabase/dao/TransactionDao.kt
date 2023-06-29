package com.developerstring.finspare.roomdatabase.dao

import androidx.room.*
import com.developerstring.finspare.roomdatabase.models.TransactionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getAllTransactions(): Flow<List<TransactionModel>>

    @Query("SELECT * FROM transaction_table WHERE id=:transactionID")
    fun getSelectedTransaction(transactionID: Int): Flow<TransactionModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transactionModel: TransactionModel)

    @Update
    suspend fun updateTransaction(transactionModel: TransactionModel)

    @Query("DELETE FROM transaction_table WHERE id=:id")
    suspend fun deleteTransaction(id: Int)

    @Query("DELETE FROM transaction_table WHERE eventID=:eventId")
    suspend fun deleteEventTransactions(eventId: Int)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAllTransactions()

    @Query("SELECT MAX(id) FROM transaction_table")
    fun getLastTransaction(): Flow<Int>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE month = :month AND year = :year AND transaction_type = :transaction_type")
    fun getMonthSum(month: String,year: String,transaction_type: String): Flow<Long?>

    @Query("SELECT * FROM transaction_table WHERE info LIKE :searchQuery OR amount LIKE :searchQuery OR category LIKE :searchQuery OR place LIKE :searchQuery OR date LIKE :searchQuery OR subCategory LIKE :searchQuery OR categoryOther LIKE :searchQuery OR subCategoryOther LIKE :searchQuery OR time LIKE :searchQuery OR transactionMode LIKE :searchQuery OR profile_name LIKE :searchQuery OR amount_type LIKE :searchQuery OR lend LIKE :searchQuery ORDER BY date DESC")
    fun searchAllTransactions(searchQuery: String): Flow<List<TransactionModel>>

    @Query("SELECT * FROM transaction_table WHERE (info LIKE :searchQuery OR amount LIKE :searchQuery OR category LIKE :searchQuery OR place LIKE :searchQuery OR date LIKE :searchQuery OR subCategory LIKE :searchQuery OR categoryOther LIKE :searchQuery OR subCategoryOther LIKE :searchQuery OR time LIKE :searchQuery OR transactionMode LIKE :searchQuery OR profile_name LIKE :searchQuery OR amount_type LIKE :searchQuery OR lend LIKE :searchQuery) AND (info LIKE :filterQuery OR category LIKE :filterQuery OR amount LIKE :filterQuery OR date LIKE :filterQuery OR place LIKE :filterQuery OR transaction_type LIKE :filterQuery OR subCategory LIKE :filterQuery OR categoryOther LIKE :filterQuery OR subCategoryOther LIKE :filterQuery OR time LIKE :filterQuery OR transactionMode LIKE :filterQuery OR profile_name LIKE :filterQuery OR amount_type LIKE :filterQuery OR lend LIKE :filterQuery) ORDER BY date DESC")
    fun filterSearchTransactions(searchQuery: String, filterQuery: String): Flow<List<TransactionModel>>

    @Query("SELECT amount FROM transaction_table WHERE month=:month AND year=:year AND transaction_type=:transaction_type")
    fun searchMonthPayment(month: String,year: String, transaction_type: String): Flow<List<Int>>

    @Query("SELECT amount FROM transaction_table WHERE day=:day AND month=:month AND year=:year AND transaction_type=:transaction_type")
    fun searchDayPayment(day: String,month: String,year: String, transaction_type: String): Flow<List<Int>>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE month = :month AND year = :year AND category LIKE :category AND transaction_type = :transaction_type")
    fun getCategorySum(month: String,year: String,category: String, transaction_type: String): Flow<Long?>

}