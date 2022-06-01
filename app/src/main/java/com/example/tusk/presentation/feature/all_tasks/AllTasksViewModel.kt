package com.example.tusk.presentation.feature.all_tasks

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.max
import kotlin.math.min

class AllTasksViewModel(
    private val allTasksUseCases: AllTasksUseCases,
    private val currentDate: Date,
): ViewModel() {

    private val _tasks = MutableLiveData<List<TaskVo>>()
    val tasks: LiveData<List<TaskVo>> = _tasks

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            _tasks.postValue(allTasksUseCases.getTasksByDay(currentDate).sortedBy {
                it.priority
            })
        }
    }

    fun addRandomTask() {
        viewModelScope.launch {
            allTasksUseCases.saveTask(TaskVo(
                id = UUID.randomUUID(),
                title = "keke",
                startDate = Date(),
                endDate = Date(),
                priority = tasks.value!!.size,
                description = "keke descr",
            ))
            fetchTasks()
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            allTasksUseCases.deleteAllTasks()
            fetchTasks()
        }
    }

    fun deleteTask(taskVo: TaskVo, itemList: List<TaskVo>) {
        viewModelScope.launch {
            allTasksUseCases.deleteTask(taskVo)
            val mutList = itemList.toMutableList()
            mutList.remove(taskVo)
            validateAll(mutList)
        }
    }

    fun itemPosChanged(itemList: List<TaskVo>, oldPosition: Int, newPosition: Int) {
        if (oldPosition == newPosition)
            return

        viewModelScope.launch {
            val changedList = itemList.subList(
                min(newPosition, oldPosition),
                max(newPosition, oldPosition) + 1
            )
            val startIndex = min(newPosition, oldPosition)
            changedList.forEachIndexed { index, taskVo ->
                taskVo.priority = index + startIndex
            }

            allTasksUseCases.updateTasks(changedList)
            fetchTasks()
        }
    }

    fun validateAll(itemList: List<TaskVo>) {
        viewModelScope.launch {
            itemList.forEachIndexed { index, taskVo ->
                taskVo.priority = index
            }
            allTasksUseCases.updateTasks(itemList)
            fetchTasks()
        }
    }

    fun updateTask(taskVo: TaskVo, newDescription: String, newName: String, newDeadline: Date) {
        taskVo.apply {
            title = newName
            endDate = newDeadline
            description = newDescription
        }

        viewModelScope.launch {
            allTasksUseCases.updateTask(taskVo)
            fetchTasks()
        }
    }

    class Factory (
        private val allTasksUseCases: AllTasksUseCases,
        private val currentDate: Date,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == AllTasksViewModel::class.java)
            return AllTasksViewModel(allTasksUseCases, currentDate) as T
        }
    }
}