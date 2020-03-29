package com.example.lab6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(
    var list: List<Movie>? = null,
    val itemClickListener: RecyclerViewItemClick? = null
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    fun clearAll() {
        (list as? ArrayList<Movie>)?.clear()
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(movie: Movie?){
//            val postImage = view.findViewById<ImageView>(R.id.postImage)
            val movieName1 = view.findViewById<TextView>(R.id.movieName1)
//            val movieName2 = view.findViewById<TextView>(R.id.movieName2)
            val genre = view.findViewById<TextView>(R.id.genre)
            val rating = view.findViewById<TextView>(R.id.rating)
            val country = view.findViewById<TextView>(R.id.country)

            movieName1.text = movie?.movieName
            genre.text = movie?.genre
            rating.text = movie?.rating.toString()
            country.text = movie?.country

            view.setOnClickListener {
                itemClickListener?.itemClick(adapterPosition, movie!!)
            }
        }
    }

    interface RecyclerViewItemClick {
        fun itemClick(position: Int, item: Movie)
    }

}