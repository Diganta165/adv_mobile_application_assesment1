package com.shafiur.bibliophase.data.repository

import com.example.booktracker.data.dao.NoteDao
import com.shafiur.bibliophase.data.model.Note
import com.shafiur.bibliophase.data.model.NoteWithBook
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun addNote(note: com.shafiur.bibliophase.data.model.Note) = noteDao.insert(note)

    suspend fun updateNote(note: com.shafiur.bibliophase.data.model.Note) = noteDao.update(note)

    suspend fun deleteNote(note: com.shafiur.bibliophase.data.model.Note) = noteDao.delete(note)

    fun getNotesForBook(bookId: Int): Flow<List<com.shafiur.bibliophase.data.model.Note>> {
        return noteDao.getNotesForBook(bookId)
    }

    fun getAllNotesWithBooks(): Flow<List<com.shafiur.bibliophase.data.model.NoteWithBook>> = noteDao.getAllNotesWithBooks()

}
