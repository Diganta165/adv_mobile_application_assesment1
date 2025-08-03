package com.example.booktracker.data.repository

import com.example.booktracker.data.dao.UserDao
import com.example.booktracker.data.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun login(email: String, password: String) = userDao.login(email, password)
    suspend fun getUserById(id: Int) = userDao.getUserById(id)

    suspend fun updatePassword(userId: Int, newPassword: String) =
        userDao.updatePassword(userId, newPassword)

}
