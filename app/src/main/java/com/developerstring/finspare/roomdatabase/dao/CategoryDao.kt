package com.developerstring.finspare.roomdatabase.dao

import androidx.room.*
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table ORDER BY category ASC")
    fun getAllCategories(): Flow<List<CategoryModel>>

    @Query("SELECT * FROM category_table WHERE id=:id")
    fun getSelectedCategory(id: Int): Flow<CategoryModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(categoryModel: CategoryModel)

    @Query("UPDATE category_table SET category=:category WHERE id=:id")
    suspend fun updateCategoryName(id: Int,category: String)

    @Query("UPDATE category_table SET subCategory=:subCategory WHERE id=:id")
    suspend fun updateSubCategoryName(id: Int,subCategory: String)

    @Delete
    suspend fun deleteCategory(categoryModel: CategoryModel)

    @Query("DELETE FROM category_table")
    suspend fun deleteAllCategories()

}