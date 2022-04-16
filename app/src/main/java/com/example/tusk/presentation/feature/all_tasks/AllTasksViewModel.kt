package com.example.tusk.presentation.feature.all_tasks

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.*

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
            _tasks.postValue(allTasksUseCases.getAllTasks())
        }
    }

    fun addRandomTask() {
        viewModelScope.launch {
            allTasksUseCases.saveTask(TaskVo(UUID.randomUUID(), "keke", Date(), Date()))
            fetchTasks()
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            allTasksUseCases.deleteAllTasks()
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