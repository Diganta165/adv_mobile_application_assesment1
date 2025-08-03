package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiur.bibliophase.data.model.Note
import com.shafiur.bibliophase.data.model.NoteWithBook
import com.example.booktracker.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    fun getNotesForBook(bookId: Int): Flow<List<com.shafiur.bibliophase.data.model.Note>> {
        return noteRepository.getNotesForBook(bookId)
    }

    fun addNote(note: com.shafiur.bibliophase.data.model.Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }

    fun updateNote(note: com.shafiur.bibliophase.data.model.Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }
    }

    fun deleteNote(note: com.shafiur.bibliophase.data.model.Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }


    fun getAllNotesWithBooks(): Flow<List<com.shafiur.bibliophase.data.model.NoteWithBook>> = noteRepository.getAllNotesWithBooks()

}

