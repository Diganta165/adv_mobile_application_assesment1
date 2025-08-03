package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import com.shafiur.bibliophase.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<com.shafiur.bibliophase.data.model.User?>(null)
    val currentUser: StateFlow<com.shafiur.bibliophase.data.model.User?> get() = _currentUser

    fun setUser(user: com.shafiur.bibliophase.data.model.User) {
        _currentUser.value = user
    }

    fun clearSession() {
        _currentUser.value = null
    }
}
