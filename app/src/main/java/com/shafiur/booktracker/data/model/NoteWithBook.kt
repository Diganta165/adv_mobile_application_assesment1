package com.shafiur.booktracker.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class NoteWithBook(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "id"
    )
    val book: Book
)
