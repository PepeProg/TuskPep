package com.example.tusk.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class TaskDto(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "start_date") val startDate: Date = Date(),
    @ColumnInfo(name = "end_date") val endDate: Date = Date(),
)