package com.developerstring.financesapp.roomdatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developerstring.financesapp.util.Constants.PROFIlE_DB_TABLE

@Entity(tableName = PROFIlE_DB_TABLE)
data class ProfileModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val total_amount: Int = 0,
    val currency: Int = 0,
    val month_spent: Int = 0,
    val month_saving: Int = 0,
    val theme: String = ""
)
