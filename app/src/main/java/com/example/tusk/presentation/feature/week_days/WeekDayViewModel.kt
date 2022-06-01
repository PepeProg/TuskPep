package com.example.tusk.presentation.feature.week_days

import androidx.lifecycle.*
import com.example.tusk.presentation.feature.all_tasks.TaskVo
import kotlinx.coroutines.launch
import java.util.*

class WeekDayViewModel(
    private val weekDaysUseCases: WeekDaysUseCases
): ViewModel() {

    private val _days = MutableLiveData<List<WeekDayVo>>()
    val days: LiveData<List<WeekDayVo>> = _days

    init {
        fetchDays(Date())
    }

    fun fetchDays(date: Date) {
        viewModelScope.launch {
            _days.postValue(weekDaysUseCases.getAllDays(date).sortedBy {
                it.order_id
            })
        }
    }

    class Factory(
        private val weekDaysUseCases: WeekDaysUseCases
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == WeekDayViewModel::class.java)
            return WeekDayViewModel(weekDaysUseCases) as T
        }
    }
}