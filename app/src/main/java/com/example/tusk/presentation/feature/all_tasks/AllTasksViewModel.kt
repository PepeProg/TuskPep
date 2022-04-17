package com.example.tusk.presentation.feature.all_tasks

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.max
import kotlin.math.min

class AllTasksViewModel(
    private val allTasksUseCases: AllTasksUseCases,
): ViewModel() {

    private val _tasks = MutableLiveData<List<TaskVo>>()
    val tasks: LiveData<List<TaskVo>> = _tasks

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            _tasks.postValue(allTasksUseCases.getAllTasks().sortedBy {
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
                priority = tasks.value!!.size
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

    class Factory (
        private val allTasksUseCases: AllTasksUseCases,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == AllTasksViewModel::class.java)
            return AllTasksViewModel(allTasksUseCases) as T
        }
    }
}