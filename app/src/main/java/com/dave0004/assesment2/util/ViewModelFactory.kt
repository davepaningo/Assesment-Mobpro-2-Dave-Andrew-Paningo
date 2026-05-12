package com.dave0004.assesment2.util


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dave0004.assesment2.database.WorkoutDb
import com.dave0004.assesment2.ui.theme.screen.DetailViewModel
import com.dave0004.assesment2.ui.theme.screen.MainViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val dao = WorkoutDb.getInstance(context).workoutDao()

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }

        throw IllegalArgumentException()
    }
}