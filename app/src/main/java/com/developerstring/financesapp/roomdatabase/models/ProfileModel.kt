package com.developerstring.financesapp.roomdatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developerstring.financesapp.util.Constants.PROFIlE_DB_TABLE

@Entity(tableName = PROFIlE_DB_TABLE)
data class ProfileModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String = "",
    val total_amount: Int = 0,
    val currency: String = "",
    val month_spent: Int = 0,
    val month_saving: Int = 0,
    val theme: String = "",
    val language: String = "",
    val time24Hours: Boolean = false
)
