package com.developerstring.finspare.roomdatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developerstring.finspare.roomdatabase.dao.CategoryDao
import com.developerstring.finspare.roomdatabase.dao.ProfileDao
import com.developerstring.finspare.roomdatabase.dao.TransactionDao
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.roomdatabase.models.TransactionModel

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