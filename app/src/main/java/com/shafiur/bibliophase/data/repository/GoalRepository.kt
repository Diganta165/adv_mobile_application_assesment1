package com.shafiur.bibliophase.data.repository

import com.shafiur.bibliophase.data.dao.GoalDao
import com.shafiur.bibliophase.data.model.ReadingGoal
import kotlinx.coroutines.flow.Flow

class GoalRepository(private val goalDao: com.shafiur.bibliophase.data.dao.GoalDao) {
    suspend fun insertGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal) = goalDao.insertGoal(goal)
    suspend fun updateGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal) = goalDao.updateGoal(goal)
    suspend fun deleteGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal) = goalDao.deleteGoal(goal)
    fun getGoalsForUser(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.ReadingGoal>> = goalDao.getGoalsForUser(userId)
}
