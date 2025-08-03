package com.shafiur.bibliophase.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shafiur.bibliophase.data.repository.GoalRepository
import com.example.booktracker.viewmodel.GoalViewModel


class GoalViewModelFactory(private val repository: com.shafiur.bibliophase.data.repository.GoalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoalViewModel(repository) as T
    }
}