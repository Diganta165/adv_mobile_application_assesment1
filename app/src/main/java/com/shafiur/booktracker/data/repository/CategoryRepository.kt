package com.shafiur.booktracker.data.repository

import com.shafiur.booktracker.data.dao.CategoryDao
import com.shafiur.booktracker.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: Category) {
        categoryDao.insert(category)
    }

    fun getCategoriesForUser(userId: Int): Flow<List<Category>> {
        return categoryDao.getCategoriesForUser(userId)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.delete(category)
    }

    suspend fun getCategoryById(id: Int): Category? {
        return categoryDao.getCategoryById(id)
    }
}
