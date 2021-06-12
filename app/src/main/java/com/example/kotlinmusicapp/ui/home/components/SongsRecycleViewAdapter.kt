package com.example.kotlinmusicapp.ui.home.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmusicapp.R
import com.example.kotlinmusicapp.data.responses.types.Song

class SongsRecycleViewAdapter(
    private val songList : List<Song>,
    private val listener : OnItemClickListener
) :
    RecyclerView.Adapter<SongsRecycleViewAdapter.SongViewHolder>() {

    inner class SongViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val nameView : TextView = itemView.findViewById(R.id.song_name)
        val authView : TextView = itemView.findViewById(R.id.song_artist)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_song_item,parent, false)
        return SongViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currentItem = songList[position]
        holder.nameView.text = currentItem.sgn_name
        holder.authView.text = currentItem.sgn_artist
    }

    override fun getItemCount() = songList.size

}