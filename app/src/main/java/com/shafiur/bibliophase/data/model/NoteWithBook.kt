package com.shafiur.bibliophase.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class NoteWithBook(
    @Embedded val note: com.shafiur.bibliophase.data.model.Note,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "id"
    )
    val book: com.shafiur.bibliophase.data.model.Book
)
