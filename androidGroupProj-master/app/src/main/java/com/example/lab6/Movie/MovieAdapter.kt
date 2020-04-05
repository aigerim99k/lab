package com.example.lab6.Movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab6.MovieApi
import com.example.lab6.R
import com.example.lab6.RetrofitService
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
                it.putExtra("id", movies[position].id)
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
    private var id: Int = 0
    fun bind(movie: Result) {
        Glide.with(itemView.context)
            .load("https://image.tmdb.org/t/p/w342${movie.poster_path}")
            .into(photo)

        id = movie.id
        movieId.text = (adapterPosition+1).toString()
        title.text = movie.title
        rating.text = movie.vote_average.toString()
        votes.text = movie.vote_count.toString()
        releaseDate.text = "("+movie.release_date+")"
        genres.text = ""
    }
}


