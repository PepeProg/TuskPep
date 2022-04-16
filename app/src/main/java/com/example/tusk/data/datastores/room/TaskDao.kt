package com.example.tusk.data.datastores.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tusk.data.dto.TaskDto
import java.util.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks() : List<TaskDto>

    @Insert
    suspend fun addTasks(tasks: List<TaskDto>)

    @Query("SELECT * FROM tasks WHERE id=(:id)")
    suspend fun getTaskById(id: UUID) : TaskDto

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}