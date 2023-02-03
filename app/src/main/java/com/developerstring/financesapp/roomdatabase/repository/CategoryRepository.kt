package com.developerstring.financesapp.roomdatabase.repository

import com.developerstring.financesapp.roomdatabase.dao.CategoryDao
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    val getAllCategories: Flow<List<CategoryModel>> = categoryDao.getAllCategories()

    fun getSelectedCategory(id: Int): Flow<CategoryModel?> {
        return categoryDao.getSelectedCategory(id)
    }

    suspend fun addCategory(categoryModel: CategoryModel) {
        categoryDao.addCategory(categoryModel = categoryModel)
    }


    suspend fun updateCategoryName(id: Int, category: String) {
        categoryDao.updateCategoryName(id = id,category = category)
    }

    suspend fun updateSubCategoryName(id: Int, subCategory: String) {
        categoryDao.updateSubCategoryName(id = id, subCategory = subCategory)
    }

    suspend fun deleteCategory(categoryModel: CategoryModel) {
        categoryDao.deleteCategory(categoryModel = categoryModel)
    }

    suspend fun deleteAllProfiles() {
        categoryDao.deleteAllCategories()
    }

}