package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiur.bibliophase.data.model.Category
import com.shafiur.bibliophase.data.repository.BookRepository
import com.shafiur.bibliophase.data.repository.CategoryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: com.shafiur.bibliophase.data.repository.CategoryRepository,
    private val bookRepository: com.shafiur.bibliophase.data.repository.BookRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<com.shafiur.bibliophase.data.model.Category>>(emptyList())
    val categories: StateFlow<List<com.shafiur.bibliophase.data.model.Category>> = _categories

    fun loadCategories(userId: Int) {
        viewModelScope.launch {
            categoryRepository.getCategoriesForUser(userId).collect {
                _categories.value = it
            }
        }
    }

    fun addCategory(userId: Int, name: String) {
        viewModelScope.launch {
            val category = com.shafiur.bibliophase.data.model.Category(userId = userId, name = name)
            categoryRepository.insertCategory(category)
        }
    }

    fun deleteCategory(category: com.shafiur.bibliophase.data.model.Category) {
        viewModelScope.launch {
            // Only delete if no books are linked to this category
            bookRepository.getBooksByCategoryId(category.userId, category.id).collect { books ->
                if (books.isEmpty()) {
                    categoryRepository.deleteCategory(category)
                }
            }
        }
    }
}
