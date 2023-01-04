package com.developerstring.financesapp.roomdatabase.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developerstring.financesapp.util.Constants.CATEGORY_DB_TABLE

@Entity(tableName = CATEGORY_DB_TABLE)
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String = "",
    val subCategory: String = ""
)
