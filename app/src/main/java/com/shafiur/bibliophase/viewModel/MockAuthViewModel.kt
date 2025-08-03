package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.shafiur.bibliophase.data.model.User

class MockAuthViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow<com.shafiur.bibliophase.data.model.User?>(null)
    val currentUser: StateFlow<com.shafiur.bibliophase.data.model.User?> = _currentUser

    fun login(email: String, password: String) {
        // simulate successful login
        _currentUser.value =
            com.shafiur.bibliophase.data.model.User(id = 1, email = email, password = password)
    }

    fun signUp(user: com.shafiur.bibliophase.data.model.User) {
        // simulate sign up
        _currentUser.value = user.copy(id = 2)
    }
}
