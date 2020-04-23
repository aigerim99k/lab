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

}

