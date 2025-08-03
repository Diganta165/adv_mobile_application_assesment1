package com.shafiur.bibliophase.data.repository

import com.shafiur.bibliophase.data.dao.UserDao
import com.shafiur.bibliophase.data.model.User

class UserRepository(private val userDao: com.shafiur.bibliophase.data.dao.UserDao) {
    suspend fun insertUser(user: com.shafiur.bibliophase.data.model.User) = userDao.insertUser(user)
    suspend fun login(email: String, password: String) = userDao.login(email, password)
    suspend fun getUserById(id: Int) = userDao.getUserById(id)

    suspend fun updatePassword(userId: Int, newPassword: String) =
        userDao.updatePassword(userId, newPassword)

}
