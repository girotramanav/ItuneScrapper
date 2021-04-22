package com.example.music

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs_table")
class Songs(
    @ColumnInfo(name = "song_name") val name : String,
    @ColumnInfo(name = "image_url") val url : String,
    @ColumnInfo(name = "artist_name") val artist : String
)
{
    @PrimaryKey(autoGenerate = true) var id = 0
}