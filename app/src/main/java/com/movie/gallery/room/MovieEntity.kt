package com.movie.gallery.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import javax.annotation.processing.Generated

@Entity(tableName = "movie_entity")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    val movieId:Long,

    @ColumnInfo(name = "movie_title")
    val movieTitle:String,

    @ColumnInfo("overview")
    val overview:String,

    @ColumnInfo("poster_path")
    val posterPath:String,

    @ColumnInfo("status")
    val status:String,

    @ColumnInfo("release_date")
    val releaseDate:String,

    @ColumnInfo("budget")
    val budget:Int
)
