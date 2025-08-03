package com.shafiur.bibliophase.data.dao

import androidx.room.*
import com.shafiur.bibliophase.data.model.Note
import com.shafiur.bibliophase.data.model.NoteWithBook
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: com.shafiur.bibliophase.data.model.Note)

    @Update
    suspend fun update(note: com.shafiur.bibliophase.data.model.Note)

    @Delete
    suspend fun delete(note: com.shafiur.bibliophase.data.model.Note)

    @Query("SELECT * FROM notes WHERE bookId = :bookId ORDER BY createdAt DESC")
    fun getNotesForBook(bookId: Int): Flow<List<com.shafiur.bibliophase.data.model.Note>>

    @Transaction
    @Query("SELECT * FROM notes")
    fun getAllNotesWithBooks(): Flow<List<com.shafiur.bibliophase.data.model.NoteWithBook>>

}
