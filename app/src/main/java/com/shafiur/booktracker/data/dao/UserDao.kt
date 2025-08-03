package com.shafiur.booktracker.data.dao

import androidx.room.*
import com.shafiur.booktracker.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

//    check user
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): User?

//    select all the user
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

//    set new password
    @Query("UPDATE users SET password = :newPassword WHERE id = :userId")
    suspend fun updatePassword(userId: Int, newPassword: String)

}
