package com.developerstring.financesapp.roomdatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developerstring.financesapp.util.Constants.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
data class TransactionModel(
    @PrimaryKey
    val id: Int = 0,
    val amount: Int = 0,
    val transaction_type: String = "",
    val category: String = "",
    val date: String = "",
    val info: String = "",
    val place: String = "",
    val eventID: String = "",
    val spent: Int = 0,
    val add_fund: Int = 0,
    val savings: Int = 0
)
