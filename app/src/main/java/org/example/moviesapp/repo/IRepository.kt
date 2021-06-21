package org.example.moviesapp.repo

interface IRepository {
    fun fetchMovies(url: String): String?
}