package com.developerstring.financesapp.roomdatabase.dao

import androidx.room.*
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table ORDER BY category ASC")
    fun getAllCategories(): Flow<List<CategoryModel>>

    @Query("SELECT * FROM category_table WHERE id=:id")
    fun getSelectedCategory(id: Int): Flow<CategoryModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(categoryModel: CategoryModel)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateCategory(categoryModel: CategoryModel)

    @Query("UPDATE category_table SET category=:category WHERE id=:id")
    suspend fun updateCategoryName(id: Int,category: String)

    @Query("UPDATE category_table SET subCategory=:subCategory WHERE id=:id")
    suspend fun updateSubCategoryName(id: Int,subCategory: String)

    @Delete
    suspend fun deleteCategory(categoryModel: CategoryModel)

}