package com.movie.gallery.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 2)
abstract class MovieDatabase:RoomDatabase() {

    abstract fun movieDao():MovieDao
}