package com.example.lab6.model
import androidx.room.*
import com.example.lab6.model.json.movie.Result

@Dao
interface MovieDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>)

    @Query("SELECT * FROM movie_table")
    fun getMovies():List<Result>

    @Query("SELECT * FROM movie_table")
    fun getFavMovies(): List<Result>

    @Update
    fun updateMovie(movie: Result)

    @Update
    fun updateAll(movies: List<Result>)

    @Query("SELECT * FROM movie_table WHERE id = :id")
    fun getMovieById(id: Int): Result

    @Query("UPDATE movie_table SET tagline = :tagline WHERE id = :id")
    fun updateMovieTagline(tagline: String, id: Int)

    @Query("UPDATE movie_table SET runtime = :runtime WHERE id = :id")
    fun updateMovieRuntime(runtime: Int, id: Int)
}

