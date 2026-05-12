package com.dave0004.assesment2.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dave0004.assesment2.model.Workout
import kotlinx.coroutines.flow.Flow
@Dao
interface WorkoutDao {


    @Insert
    suspend fun insertWorkout(workout: Workout)

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workout")
    fun getWorkout(): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE id = :id")
    suspend fun getWorkoutById(id: Long): Workout?

    @Query("DELETE FROM workout WHERE id = :id")
    suspend fun deleteById(id: Long)

}