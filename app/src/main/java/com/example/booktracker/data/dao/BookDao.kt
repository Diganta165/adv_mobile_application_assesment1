package com.example.booktracker.data.dao

import androidx.room.*
import com.example.booktracker.data.model.Book
import com.example.booktracker.data.model.BookWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Transaction
    @Query("SELECT * FROM books WHERE userId = :userId")
    fun getBooksWithCategories(userId: Int): Flow<List<BookWithCategory>>

    @Transaction
    @Query("SELECT * FROM books WHERE userId = :userId AND categoryId = :categoryId")
    fun getBooksByCategoryId(userId: Int, categoryId: Int): Flow<List<BookWithCategory>>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): Book

    @Query("UPDATE books SET status = :status WHERE id = :bookId")
    suspend fun updateStatus(bookId: Int, status: String)

    @Query("SELECT * FROM books WHERE userId = :userId")
    fun getBooksByUser(userId: Int): Flow<List<Book>>


}
