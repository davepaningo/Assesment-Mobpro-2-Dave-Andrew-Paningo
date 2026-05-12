package com.dave0004.assesment2.ui.theme.screen



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dave0004.assesment2.database.WorkoutDao
import com.dave0004.assesment2.model.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val dao: WorkoutDao
) : ViewModel() {

    fun insert(
        nama: String,
        kategori: String,
        durasi: String,
        tanggal: String,
        jam: String
    ) {
        viewModelScope.launch {
            dao.insertWorkout(
                Workout(
                    nama = nama,
                    kategori = kategori,
                    durasi = durasi,
                    tanggal = tanggal,
                    jam = jam
                )
            )
        }
    }

    suspend fun getWorkout(id: Long): Workout? {
        return dao.getWorkoutById(id)
    }
    fun update(id: Long, nama: String, kategori: String, durasi: String, tanggal: String, jam: String) {
        val workout = Workout(
            id = id,
            nama = nama,
            kategori = kategori,
            durasi = durasi,
            tanggal = tanggal,
            jam = jam
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateWorkout(workout)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }

    }
}