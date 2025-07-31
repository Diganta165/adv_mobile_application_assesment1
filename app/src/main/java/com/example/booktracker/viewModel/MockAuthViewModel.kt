package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.booktracker.data.model.User

class MockAuthViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun login(email: String, password: String) {
        // simulate successful login
        _currentUser.value = User(id = 1, email = email, password = password)
    }

    fun signUp(user: User) {
        // simulate sign up
        _currentUser.value = user.copy(id = 2)
    }
}
