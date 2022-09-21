package com.developerstring.financesapp.roomdatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developerstring.financesapp.roomdatabase.dao.TransactionDao
import com.developerstring.financesapp.roomdatabase.models.TransactionModel

// we can add our database tables here
@Database(entities = [TransactionModel::class], version = 1, exportSchema = false)
abstract class TransactionDatabase: RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

}