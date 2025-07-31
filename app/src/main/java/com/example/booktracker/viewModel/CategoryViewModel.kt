package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.Category
import com.example.booktracker.data.repository.BookRepository
import com.example.booktracker.data.repository.CategoryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    fun loadCategories(userId: Int) {
        viewModelScope.launch {
            categoryRepository.getCategoriesForUser(userId).collect {
                _categories.value = it
            }
        }
    }

    fun addCategory(userId: Int, name: String) {
        viewModelScope.launch {
            val category = Category(userId = userId, name = name)
            categoryRepository.insertCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
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
