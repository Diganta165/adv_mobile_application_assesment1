package com.example.booktracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_goals")
data class ReadingGoal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val goalType: String,
    val targetBooks: Int,
    val booksCompleted: Int = 0,
    val startDate: Long,
    val endDate: Long,
    val selectedBookIds: List<Int> // <-- ✅ ADD THIS
)
