package com.shafiur.bibliophase.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.booktracker.data.model.*
import com.example.booktracker.data.dao.*
import com.shafiur.bibliophase.data.dao.NoteDao

@Database(
    entities = [com.shafiur.bibliophase.data.model.User::class, com.shafiur.bibliophase.data.model.Book::class, com.shafiur.bibliophase.data.model.Note::class, com.shafiur.bibliophase.data.model.ReadingGoal::class, com.shafiur.bibliophase.data.model.Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(com.shafiur.bibliophase.data.dao.Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): com.shafiur.bibliophase.data.dao.UserDao
    abstract fun bookDao(): com.shafiur.bibliophase.data.dao.BookDao
    abstract fun noteDao(): NoteDao
    abstract fun goalDao(): com.shafiur.bibliophase.data.dao.GoalDao
    abstract fun categoryDao(): com.shafiur.bibliophase.data.dao.CategoryDao
}
