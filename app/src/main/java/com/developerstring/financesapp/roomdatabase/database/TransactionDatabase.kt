package com.developerstring.financesapp.roomdatabase.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.developerstring.financesapp.roomdatabase.dao.TransactionDao
import com.developerstring.financesapp.roomdatabase.models.TransactionModel

// we can add our database tables here
@Database(
    entities = [TransactionModel::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

}