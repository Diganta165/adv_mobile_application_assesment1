package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.booktracker.data.repository.BookRepository
import com.example.booktracker.data.repository.CategoryRepository

class CategoryViewModelFactory(
    private val categoryRepository: CategoryRepository,
    private val bookRepository: BookRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryRepository, bookRepository) as T
    }
}
