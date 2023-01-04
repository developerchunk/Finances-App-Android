package com.developerstring.financesapp.roomdatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developerstring.financesapp.roomdatabase.dao.CategoryDao
import com.developerstring.financesapp.roomdatabase.dao.ProfileDao
import com.developerstring.financesapp.roomdatabase.dao.TransactionDao
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import com.developerstring.financesapp.roomdatabase.models.ProfileModel
import com.developerstring.financesapp.roomdatabase.models.TransactionModel

// we can add our database tables here
@Database(
    entities = [TransactionModel::class, ProfileModel::class, CategoryModel::class],
    version = 1,
    exportSchema = true,
)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun profileDao(): ProfileDao
    abstract fun categoryDao(): CategoryDao

}