package com.shafiur.booktracker.data.dao

import androidx.room.*
import com.shafiur.booktracker.data.model.Book
import com.shafiur.booktracker.data.model.BookWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

//    select books based on user
    @Transaction
    @Query("SELECT * FROM books WHERE userId = :userId")
    fun getBooksWithCategories(userId: Int): Flow<List<BookWithCategory>>

//    select book based on cateogry
    @Transaction
    @Query("SELECT * FROM books WHERE userId = :userId AND categoryId = :categoryId")
    fun getBooksByCategoryId(userId: Int, categoryId: Int): Flow<List<BookWithCategory>>

//    all books
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): Book

//    Update books
    @Query("UPDATE books SET status = :status WHERE id = :bookId")
    suspend fun updateStatus(bookId: Int, status: String)

//    select books based on user
    @Query("SELECT * FROM books WHERE userId = :userId")
    fun getBooksByUser(userId: Int): Flow<List<Book>>


}
