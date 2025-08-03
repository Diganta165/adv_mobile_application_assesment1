package com.shafiur.booktracker.data.dao

import androidx.room.*
import com.shafiur.booktracker.data.model.Note
import com.shafiur.booktracker.data.model.NoteWithBook
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)
//    update notes
    @Update
    suspend fun update(note: Note)
//    delete notes
    @Delete
    suspend fun delete(note: Note)
//    select all notes decending order
    @Query("SELECT * FROM notes WHERE bookId = :bookId ORDER BY createdAt DESC")
    fun getNotesForBook(bookId: Int): Flow<List<Note>>

//    select all notes
    @Transaction
    @Query("SELECT * FROM notes")
    fun getAllNotesWithBooks(): Flow<List<NoteWithBook>>

}
