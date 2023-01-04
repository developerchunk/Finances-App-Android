package com.developerstring.financesapp.roomdatabase.repository

import com.developerstring.financesapp.roomdatabase.dao.CategoryDao
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    val getAllCategories: Flow<List<CategoryModel>> = categoryDao.getAllCategories()

    suspend fun addProfile(categoryModel: CategoryModel) {
        categoryDao.addCategory(categoryModel = categoryModel)
    }

    suspend fun updateCategory(categoryModel: CategoryModel) {
        categoryDao.updateCategory(categoryModel = categoryModel)
    }

    suspend fun deleteCategory(categoryModel: CategoryModel) {
        categoryDao.deleteCategory(categoryModel = categoryModel)
    }

}