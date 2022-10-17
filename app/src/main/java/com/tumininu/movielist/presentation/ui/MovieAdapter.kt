package com.tumininu.movielist.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tumininu.movielist.R
import com.tumininu.movielist.model.Movie

class MovieAdapter(val context: Context) :
    ListAdapter<Movie, MovieAdapter.ViewHolder>(MOVIE_COMPARATOR) {

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.child_movie, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
//        Glide.with(context).load("https://image.tmdb.org/t/p/original" + movie.poster_path)
//            .into(holder.image)
        holder.title.text = movie.title
        holder.overview.text = movie.overview
        holder.releaseDate.text = context.getString(R.string.release_date, movie.release_date)
        holder.voteCount.text = context.getString(R.string.vote, movie.vote_count.toString())
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val overview: TextView = itemView.findViewById(R.id.overview)
        val releaseDate: TextView = itemView.findViewById(R.id.releaseDate)
        val voteCount: TextView = itemView.findViewById(R.id.vote)
    }

}