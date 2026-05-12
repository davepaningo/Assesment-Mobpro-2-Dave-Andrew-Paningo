package com.dave0004.assesment2.ui.theme.screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dave0004.assesment2.database.WorkoutDao
import com.dave0004.assesment2.model.Workout
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class MainViewModel(dao: WorkoutDao) : ViewModel() {
    val data: StateFlow<List<Workout>> = dao.getWorkout().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )
    fun getWorkout(id: Long): Workout? {
        return data.value.find { it.id == id }
    }
}