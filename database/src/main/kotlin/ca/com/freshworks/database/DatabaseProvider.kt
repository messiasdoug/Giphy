package ca.com.freshworks.database

import android.content.Context
import androidx.room.Room

class DatabaseProvider private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: GiphyDatabase? = null

        private const val DATABASE_NAME = "giphydatabase"

        fun dataBase(context: Context): GiphyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GiphyDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}