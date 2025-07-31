package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.model.Book
import com.example.booktracker.data.model.BookWithCategory
import com.example.booktracker.data.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun getAllBooksWithCategory(userId: Int): Flow<List<BookWithCategory>> {
        return repository.getBooksWithCategory(userId)
    }

    fun getBooksByCategoryId(userId: Int, categoryId: Int): Flow<List<BookWithCategory>> {
        return repository.getBooksByCategoryId(userId, categoryId)
    }

    fun getAllBooks(userId: Int): Flow<List<Book>> {
        return repository.getBooksByUser(userId)
    }

}
