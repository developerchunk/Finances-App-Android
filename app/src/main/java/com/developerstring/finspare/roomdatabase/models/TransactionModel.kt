package com.developerstring.finspare.roomdatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developerstring.finspare.util.Constants.TRANSACTION_DB_TABLE

@Entity(tableName = TRANSACTION_DB_TABLE)
data class TransactionModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Int = 0,
    val transaction_type: String = "",
    val transactionMode: String = "",
    val transactionModeOther: String = "",
    val category: String = "",
    val date: String = "",
    val day: Short = 0,
    val time: String = "",
    val month: Short = 0,
    val year: Short = 0,
    val info: String = "",
    val place: String = "",
    val eventID: String = "",
    val subCategory: String = "",
    val categoryOther: String = "",
    val subCategoryOther: String = "",
)
