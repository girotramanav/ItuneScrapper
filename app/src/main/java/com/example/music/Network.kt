package com.example.music

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://itunes.apple.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface Network {
    @GET("search")
    fun networkCall(@Query("term") artist: String,@Query("attribute") searchType : String = "allArtistTerm") :
            Call<JsonObject>
}

object MusicApi{
    val retrofitService : Network by lazy {
        retrofit.create(Network::class.java)
    }
}
