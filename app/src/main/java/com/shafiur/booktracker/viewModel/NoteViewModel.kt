package com.shafiur.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiur.booktracker.data.model.Note
import com.shafiur.booktracker.data.model.NoteWithBook
import com.shafiur.booktracker.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    fun getNotesForBook(bookId: Int): Flow<List<Note>> {
        return noteRepository.getNotesForBook(bookId)
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }


    fun getAllNotesWithBooks(): Flow<List<NoteWithBook>> = noteRepository.getAllNotesWithBooks()

}

