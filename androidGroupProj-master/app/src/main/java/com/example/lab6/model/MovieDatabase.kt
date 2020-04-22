package com.example.lab6.model

import android.content.Context
import androidx.room.*
import com.example.lab6.json.GenresConverter
import com.example.lab6.json.movie.Result


@Database(entities = [Result::class],version = 1,exportSchema = false)
@TypeConverters(GenresConverter::class)
abstract class MovieDatabase:RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "app_database.db"
                ).build()
            }
            return INSTANCE!!
        }
    }



}