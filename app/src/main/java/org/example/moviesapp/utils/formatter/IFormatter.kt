package org.example.moviesapp.utils.formatter

import org.example.moviesapp.model.MyItem
import org.example.moviesapp.room.MovieEnty

interface IFormatter {

    fun formatDataToListMyItem(dataStringJson: String): List<MyItem>

    fun formatDataToListMovieEnty(dataStringJson: String): List<MovieEnty>
}