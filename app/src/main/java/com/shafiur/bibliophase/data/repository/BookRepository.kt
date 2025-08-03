package com.shafiur.bibliophase.data.repository

import com.shafiur.bibliophase.data.dao.BookDao
import com.shafiur.bibliophase.data.model.Book
import com.shafiur.bibliophase.data.model.BookWithCategory
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: com.shafiur.bibliophase.data.dao.BookDao) {

    suspend fun insertBook(book: com.shafiur.bibliophase.data.model.Book) = bookDao.insert(book)

    suspend fun updateBook(book: com.shafiur.bibliophase.data.model.Book) = bookDao.update(book)

    suspend fun deleteBook(book: com.shafiur.bibliophase.data.model.Book) = bookDao.delete(book)

    fun getBooksWithCategory(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.BookWithCategory>> =
        bookDao.getBooksWithCategories(userId)

    suspend fun getBookById(bookId: Int): com.shafiur.bibliophase.data.model.Book = bookDao.getBookById(bookId)

    fun getBooksByCategoryId(userId: Int, categoryId: Int): Flow<List<com.shafiur.bibliophase.data.model.BookWithCategory>> =
        bookDao.getBooksByCategoryId(userId, categoryId)

    suspend fun updateStatus(bookId: Int, status: String) =
        bookDao.updateStatus(bookId, status)

    fun getBooksByUser(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.Book>> =
        bookDao.getBooksByUser(userId)

}
