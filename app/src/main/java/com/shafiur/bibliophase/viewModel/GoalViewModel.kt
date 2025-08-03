package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiur.bibliophase.data.model.ReadingGoal
import com.shafiur.bibliophase.data.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GoalViewModel(private val repository: com.shafiur.bibliophase.data.repository.GoalRepository) : ViewModel() {

    fun addGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal) {
        viewModelScope.launch { repository.insertGoal(goal) }
    }

    fun updateGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal) {
        viewModelScope.launch { repository.updateGoal(goal) }
    }

    fun deleteGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal) {
        viewModelScope.launch { repository.deleteGoal(goal) }
    }

    fun getGoalsForUser(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.ReadingGoal>> =
        repository.getGoalsForUser(userId)
}

