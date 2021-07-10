package com.example.music

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Songs::class], version = 1, exportSchema = false)
abstract class SongsDatabase : RoomDatabase() {

    abstract fun getSongDao() : SongDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SongsDatabase? = null

        fun getDatabase(context: Context): SongsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongsDatabase::class.java,
                    "song_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}