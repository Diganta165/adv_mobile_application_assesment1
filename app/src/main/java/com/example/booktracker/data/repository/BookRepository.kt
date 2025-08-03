package com.example.booktracker.data.repository

import com.example.booktracker.data.dao.BookDao
import com.example.booktracker.data.model.Book
import com.example.booktracker.data.model.BookWithCategory
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {

    suspend fun insertBook(book: Book) = bookDao.insert(book)

    suspend fun updateBook(book: Book) = bookDao.update(book)

    suspend fun deleteBook(book: Book) = bookDao.delete(book)

    fun getBooksWithCategory(userId: Int): Flow<List<BookWithCategory>> =
        bookDao.getBooksWithCategories(userId)

    suspend fun getBookById(bookId: Int): Book = bookDao.getBookById(bookId)

    fun getBooksByCategoryId(userId: Int, categoryId: Int): Flow<List<BookWithCategory>> =
        bookDao.getBooksByCategoryId(userId, categoryId)

    suspend fun updateStatus(bookId: Int, status: String) =
        bookDao.updateStatus(bookId, status)

    fun getBooksByUser(userId: Int): Flow<List<Book>> =
        bookDao.getBooksByUser(userId)

}
