package com.shafiur.booktracker.data.dao

import androidx.room.*
import com.shafiur.booktracker.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

//    select categories based on user
    @Query("SELECT * FROM categories WHERE userId = :userId")
    fun getCategoriesForUser(userId: Int): Flow<List<Category>>

//    Delete category
    @Delete
    suspend fun delete(category: Category)

//    select all the category
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): Category?
}
