package com.shafiur.bibliophase.data.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: com.shafiur.bibliophase.data.db.AppDatabase? = null

    fun getDatabase(context: Context): com.shafiur.bibliophase.data.db.AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                com.shafiur.bibliophase.data.db.AppDatabase::class.java,
                "book_tracker_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
