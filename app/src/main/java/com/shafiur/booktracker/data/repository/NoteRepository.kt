package com.shafiur.booktracker.data.repository

import com.shafiur.booktracker.data.dao.NoteDao
import com.shafiur.booktracker.data.model.Note
import com.shafiur.booktracker.data.model.NoteWithBook
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun addNote(note: Note) = noteDao.insert(note)

    suspend fun updateNote(note: Note) = noteDao.update(note)

    suspend fun deleteNote(note: Note) = noteDao.delete(note)

    fun getNotesForBook(bookId: Int): Flow<List<Note>> {
        return noteDao.getNotesForBook(bookId)
    }

    fun getAllNotesWithBooks(): Flow<List<NoteWithBook>> = noteDao.getAllNotesWithBooks()

}
