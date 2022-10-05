package com.developerstring.financesapp.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
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

    @Delete
    suspend fun deleteTransaction(transactionModel: TransactionModel)

    @Query("DELETE FROM transaction_table WHERE eventID=:eventId")
    suspend fun deleteEventTransactions(eventId: Int)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAllTransactions()

    @Query("SELECT * FROM transaction_table WHERE amount LIKE :searchQuery OR transaction_type LIKE :searchQuery OR category LIKE :searchQuery OR info LIKE :searchQuery OR place LIKE :searchQuery")
    fun searchAllTransactions(searchQuery: String): Flow<List<TransactionModel>>

    @Query("SELECT amount FROM transaction_table WHERE month=:month AND year=:year AND transaction_type=:transaction_type")
    fun searchMonthPayment(month: String,year: String, transaction_type: String): Flow<List<Int>>

    @Query("SELECT amount FROM transaction_table WHERE day=:day AND month=:month AND year=:year AND transaction_type=:transaction_type")
    fun searchDayPayment(day: String,month: String,year: String, transaction_type: String): Flow<List<Int>>

}