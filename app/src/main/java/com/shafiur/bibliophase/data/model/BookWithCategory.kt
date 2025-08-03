package com.shafiur.bibliophase.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class BookWithCategory(
    @Embedded val book: com.shafiur.bibliophase.data.model.Book,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: com.shafiur.bibliophase.data.model.Category?
)
