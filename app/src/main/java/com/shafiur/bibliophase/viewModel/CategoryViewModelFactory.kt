package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shafiur.bibliophase.data.repository.BookRepository
import com.shafiur.bibliophase.data.repository.CategoryRepository

class CategoryViewModelFactory(
    private val categoryRepository: com.shafiur.bibliophase.data.repository.CategoryRepository,
    private val bookRepository: com.shafiur.bibliophase.data.repository.BookRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryRepository, bookRepository) as T
    }
}
