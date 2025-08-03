package com.example.booktracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.booktracker.data.model.*
import com.example.booktracker.data.dao.*

@Database(
    entities = [User::class, Book::class, Note::class, ReadingGoal::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
    abstract fun noteDao(): NoteDao
    abstract fun goalDao(): GoalDao
    abstract fun categoryDao(): CategoryDao
}
