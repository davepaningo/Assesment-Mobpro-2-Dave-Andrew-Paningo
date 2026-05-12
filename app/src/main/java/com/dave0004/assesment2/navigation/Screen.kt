package com.dave0004.assesment2.navigation

const val  KEY_ID_WORKOUT = "idWorkout"
sealed class Screen(val route:String) {
    data object  Home: Screen("mainscreen")
    data object  FormBaru: Screen("detailScreen")
    data object  FormUbah: Screen("detailScreen/{$KEY_ID_WORKOUT}") {
        fun withId(id:Long) = "detailScreen/$id"
    }
}