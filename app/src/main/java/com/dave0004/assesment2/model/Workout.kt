package com.dave0004.assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class Workout(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nama: String,
    val kategori: String,
    val durasi: String
)