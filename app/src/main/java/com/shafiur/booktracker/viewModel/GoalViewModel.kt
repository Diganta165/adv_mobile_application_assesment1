package com.shafiur.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiur.booktracker.data.model.ReadingGoal
import com.shafiur.booktracker.data.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GoalViewModel(private val repository: GoalRepository) : ViewModel() {

    fun addGoal(goal: ReadingGoal) {
        viewModelScope.launch { repository.insertGoal(goal) }
    }

    fun updateGoal(goal: ReadingGoal) {
        viewModelScope.launch { repository.updateGoal(goal) }
    }

    fun deleteGoal(goal: ReadingGoal) {
        viewModelScope.launch { repository.deleteGoal(goal) }
    }

    fun getGoalsForUser(userId: Int): Flow<List<ReadingGoal>> =
        repository.getGoalsForUser(userId)
}

