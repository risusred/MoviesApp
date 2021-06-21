package org.example.moviesapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie_enty_table")
data class MovieEnty(
    @ColumnInfo(name = "originalTitle") val originalTitle: String,
    @ColumnInfo(name = "posterPath") val posterPath: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "originalLanguage") val originalLanguage: String
){

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}
