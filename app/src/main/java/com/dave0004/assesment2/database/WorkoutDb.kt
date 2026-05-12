package com.dave0004.assesment2.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dave0004.assesment2.model.Workout

@Database(
    entities = [Workout::class],
    version = 2,
    exportSchema = false
)
abstract class WorkoutDb : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    companion object {

        @Volatile
        private var INSTANCE: WorkoutDb? = null

        fun getInstance(context: Context): WorkoutDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDb::class.java,
                    "workout.db"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}