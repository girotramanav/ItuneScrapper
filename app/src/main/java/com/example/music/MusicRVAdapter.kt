package com.example.music

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MusicRVAdapter(private val context : Context) : RecyclerView.Adapter<MusicRVAdapter.SongsViewHolder>() {

    private var allSongs : List<Songs> = emptyList()

    inner class SongsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.image)
        val songName : TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        return SongsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_song,parent,false))
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val currentSong = allSongs[position]
        Glide.with(context).load(currentSong.url).into(holder.image)
        holder.songName.text = currentSong.name
    }

    override fun getItemCount(): Int {
        return allSongs.size
    }

    fun updateList(newList : List<Songs>)
    {
        allSongs = emptyList()
        allSongs = newList
        notifyDataSetChanged()
    }
}