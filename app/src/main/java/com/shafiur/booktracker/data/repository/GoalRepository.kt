package com.shafiur.booktracker.data.repository

import com.shafiur.booktracker.data.dao.GoalDao
import com.shafiur.booktracker.data.model.ReadingGoal
import kotlinx.coroutines.flow.Flow

class GoalRepository(private val goalDao: GoalDao) {
    suspend fun insertGoal(goal: ReadingGoal) = goalDao.insertGoal(goal)
    suspend fun updateGoal(goal: ReadingGoal) = goalDao.updateGoal(goal)
    suspend fun deleteGoal(goal: ReadingGoal) = goalDao.deleteGoal(goal)
    fun getGoalsForUser(userId: Int): Flow<List<ReadingGoal>> = goalDao.getGoalsForUser(userId)
}
