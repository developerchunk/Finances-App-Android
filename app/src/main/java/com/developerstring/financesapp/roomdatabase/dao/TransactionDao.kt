package com.developerstring.financesapp.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

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

    @Delete
    suspend fun deleteTransaction(transactionModel: TransactionModel)

    @Query("DELETE FROM transaction_table WHERE eventID=:eventId")
    suspend fun deleteEventTransactions(eventId: Int)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAllTransactions()

    @Query("SELECT MAX(id) FROM transaction_table")
    fun getLastTransaction(): Flow<Int>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE month = :month AND year = :year AND transaction_type = :transaction_type")
    fun getMonthSum(month: String,year: String,transaction_type: String): Flow<Long?>

    @Query("SELECT * FROM transaction_table WHERE info LIKE :searchQuery OR amount LIKE :searchQuery OR category LIKE :searchQuery OR place LIKE :searchQuery OR date LIKE :searchQuery OR subCategory LIKE :searchQuery OR categoryOther LIKE :searchQuery OR subCategoryOther LIKE :searchQuery ORDER BY date DESC")
    fun searchAllTransactions(searchQuery: String): Flow<List<TransactionModel>>

    @Query("SELECT * FROM transaction_table WHERE (info LIKE :searchQuery OR amount LIKE :searchQuery OR category LIKE :searchQuery OR place LIKE :searchQuery OR date LIKE :searchQuery OR subCategory LIKE :searchQuery OR categoryOther LIKE :searchQuery OR subCategoryOther LIKE :searchQuery) AND (info LIKE :filterQuery OR category LIKE :filterQuery OR amount LIKE :filterQuery OR date LIKE :filterQuery OR place LIKE :filterQuery OR transaction_type LIKE :filterQuery OR subCategory LIKE :filterQuery OR categoryOther LIKE :filterQuery OR subCategoryOther LIKE :filterQuery) ORDER BY date DESC")
    fun filterSearchTransactions(searchQuery: String, filterQuery: String): Flow<List<TransactionModel>>

    @Query("SELECT amount FROM transaction_table WHERE month=:month AND year=:year AND transaction_type=:transaction_type")
    fun searchMonthPayment(month: String,year: String, transaction_type: String): Flow<List<Int>>

    @Query("SELECT amount FROM transaction_table WHERE day=:day AND month=:month AND year=:year AND transaction_type=:transaction_type")
    fun searchDayPayment(day: String,month: String,year: String, transaction_type: String): Flow<List<Int>>

}