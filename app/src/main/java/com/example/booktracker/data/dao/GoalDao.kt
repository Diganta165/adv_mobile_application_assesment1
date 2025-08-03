package com.example.booktracker.data.dao

import androidx.room.*
import com.example.booktracker.data.model.ReadingGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: ReadingGoal): Long

    @Update
    suspend fun updateGoal(goal: ReadingGoal)

    @Delete
    suspend fun deleteGoal(goal: ReadingGoal)

    @Query("SELECT * FROM reading_goals WHERE userId = :userId")
    fun getGoalsForUser(userId: Int): Flow<List<ReadingGoal>>
}
class Converters {
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString(",")

    @TypeConverter
    fun toList(data: String): List<Int> =
        if (data.isEmpty()) emptyList()
        else data.split(",").map { it.toInt() }
}
