package com.example.music

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MusicViewModel(application: Application) : AndroidViewModel(application) {

    private val _songList = MutableLiveData<ArrayList<Songs>>()
    val songList : LiveData<ArrayList<Songs>> = _songList
//    private val repository : Repository
//
//    init {
//        val dao = SongsDatabase.getDatabase(application).getSongDao()
//        repository = Repository(dao)
//        songList = repository.songList
//    }

     fun getResponse(artist : String)
     {
         getFromAPI(artist)
     }


    private fun getFromAPI(artist : String)
    {
        MusicApi.retrofitService.networkCall(artist).enqueue(
                object : Callback<JsonObject> {

                    override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>
                    ) {
                            val jsonObject = response.body()
                            val jsonArray = jsonObject?.getAsJsonArray("results")
                            val list = ArrayList<Songs>()
                            for (i in 0 until jsonArray!!.size()) {
                                try {
                                    val songObject: JsonObject = jsonArray[i].asJsonObject
                                    val result = Songs(
                                            songObject.get("collectionName").asString,
                                            songObject.get("artworkUrl100").asString,
                                            songObject.get("artistName").asString
                                    )
                                    list.add(result)
                                }
                                catch (e : java.lang.Exception)
                                {
                                    continue
                                }
                            }
                            _songList.value = list
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("infoove", t.message.toString())
                    }
                }
        )
    }

}


