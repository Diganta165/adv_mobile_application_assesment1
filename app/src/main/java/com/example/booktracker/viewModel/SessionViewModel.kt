package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.booktracker.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _currentUser

    fun setUser(user: User) {
        _currentUser.value = user
    }

    fun clearSession() {
        _currentUser.value = null
    }
}
