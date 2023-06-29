package com.developerstring.finspare.roomdatabase.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developerstring.finspare.util.Constants.PROFIlE_DB_TABLE

@Entity(tableName = PROFIlE_DB_TABLE)
data class ProfileModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val total_amount: Int = 0,
    val currency: String = "",
    val month_spent: Int = 0,
    val month_saving: Int = 0,
    val theme: String = "",
    val language: String = "",
    val time24Hours: Boolean = false,
    @ColumnInfo(name = "amount_type", defaultValue = "")
    val amount_type: String = "",
    @ColumnInfo(name = "ph_no", defaultValue = "")
    val ph_no: String = "",
    @ColumnInfo(name = "email", defaultValue = "")
    val email: String = "",
    @ColumnInfo(name = "place", defaultValue = "")
    val place: String = "",
    @ColumnInfo(name = "extra_info", defaultValue = "")
    val extra_info: String = ""
)
