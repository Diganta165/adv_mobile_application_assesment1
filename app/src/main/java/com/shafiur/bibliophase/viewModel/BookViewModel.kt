package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiur.bibliophase.data.model.Book
import com.shafiur.bibliophase.data.model.BookWithCategory
import com.shafiur.bibliophase.data.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookViewModel(private val repository: com.shafiur.bibliophase.data.repository.BookRepository) : ViewModel() {

    fun addBook(book: com.shafiur.bibliophase.data.model.Book) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }

    fun updateBook(book: com.shafiur.bibliophase.data.model.Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: com.shafiur.bibliophase.data.model.Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun getAllBooksWithCategory(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.BookWithCategory>> {
        return repository.getBooksWithCategory(userId)
    }

    fun getBooksByCategoryId(userId: Int, categoryId: Int): Flow<List<com.shafiur.bibliophase.data.model.BookWithCategory>> {
        return repository.getBooksByCategoryId(userId, categoryId)
    }

    fun getAllBooks(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.Book>> {
        return repository.getBooksByUser(userId)
    }

}
