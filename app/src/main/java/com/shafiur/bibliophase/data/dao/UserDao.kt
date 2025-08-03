package com.shafiur.bibliophase.data.dao

import androidx.room.*
import com.shafiur.bibliophase.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: com.shafiur.bibliophase.data.model.User): Long

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): com.shafiur.bibliophase.data.model.User?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): com.shafiur.bibliophase.data.model.User?

    @Query("UPDATE users SET password = :newPassword WHERE id = :userId")
    suspend fun updatePassword(userId: Int, newPassword: String)

}
