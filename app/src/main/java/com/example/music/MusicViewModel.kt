package com.example.music

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class MusicViewModel(application: Application) : AndroidViewModel(application) {

    val songList : LiveData<List<Songs>>
    private val repository : Repository

    init {
        val dao = SongsDatabase.getDatabase(application).getSongDao()
        repository = Repository(dao)
        songList = repository.songList
    }

    fun getResponse(artist : String) = viewModelScope.launch(Dispatchers.IO) {
         repository.getSongs(artist)
    }

    fun getResponseFromNetwork(artist: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getFromNetwork(artist)
    }
}


