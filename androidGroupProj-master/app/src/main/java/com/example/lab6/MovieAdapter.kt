package com.example.lab6

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab6.json.Result

class MoviesAdapter(val movies: List<Result>, val context: Context): RecyclerView.Adapter<MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieDetailActivity::class.java).also {
                it.putExtra(
                    "poster",
                    "https://image.tmdb.org/t/p/w342${movies[position].backdrop_path}"
                )
                it.putExtra("title", movies[position].title)
                it.putExtra("release", movies[position].release_date)
                it.putExtra("overview", movies[position].overview)
                it.putExtra("rating", movies[position].vote_average)
            }
            context.startActivity(intent)
        }
        return holder.bind(movies[position])
    }
}

class MoviesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    private val photo:ImageView = itemView.findViewById(R.id.movie_photo)
    private val title:TextView = itemView.findViewById(R.id.movie_title)
    private val rating:TextView = itemView.findViewById(R.id.movie_rating)
    private val votes:TextView = itemView.findViewById(R.id.movie_votes)
    private val releaseDate:TextView = itemView.findViewById(R.id.release_date)
    private val movieId:TextView = itemView.findViewById(R.id.movie_id)
    private val genres:TextView = itemView.findViewById(R.id.genres)

    fun bind(movie: Result) {
        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/w342${movie.poster_path}")
            .into(photo)

        movieId.text = (adapterPosition+1).toString()
        title.text = movie.title
        rating.text = movie.vote_average.toString()
        votes.text = movie.vote_count.toString()
        releaseDate.text = "("+movie.release_date+")"
        genres.text = ""
    }
}

