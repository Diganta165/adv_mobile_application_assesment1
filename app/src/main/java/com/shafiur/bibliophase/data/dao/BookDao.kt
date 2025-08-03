package com.shafiur.bibliophase.data.dao

import androidx.room.*
import com.shafiur.bibliophase.data.model.Book
import com.shafiur.bibliophase.data.model.BookWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: com.shafiur.bibliophase.data.model.Book)

    @Update
    suspend fun update(book: com.shafiur.bibliophase.data.model.Book)

    @Delete
    suspend fun delete(book: com.shafiur.bibliophase.data.model.Book)

    @Transaction
    @Query("SELECT * FROM books WHERE userId = :userId")
    fun getBooksWithCategories(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.BookWithCategory>>

    @Transaction
    @Query("SELECT * FROM books WHERE userId = :userId AND categoryId = :categoryId")
    fun getBooksByCategoryId(userId: Int, categoryId: Int): Flow<List<com.shafiur.bibliophase.data.model.BookWithCategory>>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): com.shafiur.bibliophase.data.model.Book

    @Query("UPDATE books SET status = :status WHERE id = :bookId")
    suspend fun updateStatus(bookId: Int, status: String)

    @Query("SELECT * FROM books WHERE userId = :userId")
    fun getBooksByUser(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.Book>>


}
