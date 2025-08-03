package com.shafiur.bibliophase.data.dao

import androidx.room.*
import com.shafiur.bibliophase.data.model.ReadingGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal): Long

    @Update
    suspend fun updateGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal)

    @Delete
    suspend fun deleteGoal(goal: com.shafiur.bibliophase.data.model.ReadingGoal)

    @Query("SELECT * FROM reading_goals WHERE userId = :userId")
    fun getGoalsForUser(userId: Int): Flow<List<com.shafiur.bibliophase.data.model.ReadingGoal>>
}
class Converters {
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString(",")

    @TypeConverter
    fun toList(data: String): List<Int> =
        if (data.isEmpty()) emptyList()
        else data.split(",").map { it.toInt() }
}
