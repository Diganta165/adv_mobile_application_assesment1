package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.booktracker.data.repository.BookRepository

class BookViewModelFactory(
    private val repository: BookRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookViewModel(repository) as T
    }
}
