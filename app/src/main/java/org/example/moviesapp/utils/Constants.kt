package org.example.moviesapp.utils

class Constants {

    companion object {
        private const val apiKey = "api_key=f321a808e68611f41312aa8408531476" //TODO REMOVE
        //https://api.themoviedb.org/3/search/movie?api_key=f321a808e68611f41312aa8408531476&query=A
        //https://api.themoviedb.org/3/movie/top_rated?api_key=f321a808e68611f41312aa8408531476

        private const val baseUrl = "https://api.themoviedb.org/3/"
        private const val topRatedUrl = "movie/top_rated?"
        const val fullUrlTopRated = baseUrl + topRatedUrl + apiKey
        private const val byTitle = "search/movie?$apiKey&query="
        const val fullUrlSearchByTitle = baseUrl + byTitle // + user search

    }
}