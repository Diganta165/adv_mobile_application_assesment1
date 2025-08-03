package com.shafiur.bibliophase.data.dao

import androidx.room.*
import com.shafiur.bibliophase.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: com.shafiur.bibliophase.data.model.Category)

    @Query("SELECT * FROM categories WHERE userId = :userId")
    fun getCategoriesForUser(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.Category>>

    @Delete
    suspend fun delete(category: com.shafiur.bibliophase.data.model.Category)

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): com.shafiur.bibliophase.data.model.Category?
}
