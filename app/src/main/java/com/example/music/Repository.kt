package com.example.music

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val songDao: SongDao) {

    private var _songList = MutableLiveData<List<Songs>>()
    val songList : LiveData<List<Songs>> = _songList

    suspend fun getFromNetwork(artist: String)
    {
        networkCall(artist)
    }

    suspend fun getSongs(artist : String)
    {
        Log.d("infor", "Serching in db")
        val dbResult : List<Songs> = songDao.getSongs("%$artist%")
        if(dbResult.isEmpty())
        {
            Log.d("infor", "In network Call")
            getFromNetwork(artist)
        }
        else
        {
            _songList.postValue(dbResult)
        }
    }

    private suspend fun insert(song : Songs)
    {
        songDao.insert(song)
    }

    private suspend fun networkCall(artist : String)
    {
        val list = mutableListOf<Songs>()
        MusicApi.retrofitService.networkCall(artist).enqueue(
                object : Callback<JsonObject> {

                    override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>
                    ) {
                            val jsonObject = response.body()
                            val jsonArray = jsonObject?.getAsJsonArray("results")
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
                                catch (e : Exception)
                                {
                                    continue
                                }
                            }
                        _songList.value = list
                        Log.d("infor" , list.size.toString())
                        GlobalScope.launch {
                            for (i in 0 until list.size)
                            {
                                insert(list[i])
                            }
                        }
                        Log.d("infor" , list.size.toString())
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("infoove", t.message.toString())
                    }
                }
        )
    }
}
