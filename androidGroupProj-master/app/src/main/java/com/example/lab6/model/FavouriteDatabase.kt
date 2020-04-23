package com.example.lab6.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab6.model.json.GenresConverter
import com.example.lab6.model.json.movie.Result

@Database(entities = [Result::class],version = 1,exportSchema = false)
@TypeConverters(GenresConverter::class)
abstract class FavouriteDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao

    companion object {

        var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, "fav_database.db"
                ).build()
            }
            return INSTANCE!!
        }
    }
}