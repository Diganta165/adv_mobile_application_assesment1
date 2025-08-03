package com.example.booktracker.data.dao

import androidx.room.*
import com.example.booktracker.data.model.Note
import com.example.booktracker.data.model.NoteWithBook
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE bookId = :bookId ORDER BY createdAt DESC")
    fun getNotesForBook(bookId: Int): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM notes")
    fun getAllNotesWithBooks(): Flow<List<NoteWithBook>>

}
