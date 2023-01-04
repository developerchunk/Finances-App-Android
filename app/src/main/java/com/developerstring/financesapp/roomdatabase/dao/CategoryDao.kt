package com.developerstring.financesapp.roomdatabase.dao

import androidx.room.*
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table ORDER BY category ASC")
    fun getAllCategories(): Flow<List<CategoryModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(categoryModel: CategoryModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateCategory(categoryModel: CategoryModel)

    @Delete
    suspend fun deleteCategory(categoryModel: CategoryModel)

}