package com.example.lab6.json
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Result>)

    @Query("SELECT * FROM movie_table")
    fun getAll():List<Result>


}

