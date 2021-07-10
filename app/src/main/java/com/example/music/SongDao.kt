package com.example.music

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(song : Songs)

    @Query("Select * from songs_table where artist_name like :artist order by id asc")
    fun getSongs(artist : String) : List<Songs>
}