package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiur.bibliophase.data.model.User
import com.example.booktracker.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<com.shafiur.bibliophase.data.model.User?>(null)
    val currentUser: StateFlow<com.shafiur.bibliophase.data.model.User?> = _currentUser

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.login(email, password)
                if (user != null) {
                    _currentUser.value = user
                    _error.value = null
                } else {
                    _error.value = "Invalid email or password"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Something went wrong"
            }
        }
    }

    fun signUp(user: com.shafiur.bibliophase.data.model.User) {
        viewModelScope.launch {
            try {
                val existing = repository.login(user.email, user.password)
                if (existing != null) {
                    _error.value = "User already exists"
                } else {
                    val id = repository.insertUser(user)
                    _currentUser.value = user.copy(id = id.toInt())
                    _error.value = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Signup failed"
            }
        }
    }
    fun clearAuthState() {
        _currentUser.value = null
        _error.value = null
    }

    fun changePassword(userId: Int, newPassword: String) {
        viewModelScope.launch {
            try {
                repository.updatePassword(userId, newPassword)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to update password"
            }
        }
    }
    fun setError(message: String) {
        _error.value = message
    }


}
