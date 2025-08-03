package com.shafiur.bibliophase.data.repository

import com.shafiur.bibliophase.data.dao.CategoryDao
import com.shafiur.bibliophase.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: com.shafiur.bibliophase.data.dao.CategoryDao) {

    suspend fun insertCategory(category: com.shafiur.bibliophase.data.model.Category) {
        categoryDao.insert(category)
    }

    fun getCategoriesForUser(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.Category>> {
        return categoryDao.getCategoriesForUser(userId)
    }

    suspend fun deleteCategory(category: com.shafiur.bibliophase.data.model.Category) {
        categoryDao.delete(category)
    }

    suspend fun getCategoryById(id: Int): com.shafiur.bibliophase.data.model.Category? {
        return categoryDao.getCategoryById(id)
    }
}
