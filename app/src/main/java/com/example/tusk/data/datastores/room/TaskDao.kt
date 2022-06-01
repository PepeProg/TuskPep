package com.example.tusk.data.datastores.room

import androidx.room.*
import com.example.tusk.data.dto.TaskDto
import java.util.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks() : List<TaskDto>

    @Insert
    suspend fun addTasks(tasks: List<TaskDto>)

    @Update
    suspend fun updateTasks(tasks: List<TaskDto>)

    @Update
    suspend fun updateTask(task: TaskDto)

    @Query("SELECT * FROM tasks WHERE id=(:id)")
    suspend fun getTaskById(id: UUID) : TaskDto

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    @Delete
    suspend fun deleteTask(task: TaskDto)
}