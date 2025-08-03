package com.shafiur.booktracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shafiur.booktracker.data.repository.GoalRepository
import com.shafiur.booktracker.viewmodel.GoalViewModel


class GoalViewModelFactory(private val repository: GoalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoalViewModel(repository) as T
    }
}