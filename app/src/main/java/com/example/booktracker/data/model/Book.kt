package com.example.booktracker.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL // optional: SET_NULL or CASCADE
        )
    ]
)
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val title: String,
    val author: String,
    val coverImageUrl: String? = null,
    val categoryId: Int?,
    val currentPage: Int = 0,
    val status: String = "Not Started",
    val dateAdded: Long = System.currentTimeMillis()
)

