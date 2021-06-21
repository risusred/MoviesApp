package org.example.moviesapp.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData


class MovieEntyRoomRepository(private val movieEntyDao: MovieEntyDao) {

    val allMovieEnties: LiveData<List<MovieEnty>> = movieEntyDao.getAllOrderByName()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(enty: MovieEnty) { movieEntyDao.insert(enty) }

    @WorkerThread
    fun deleteAll() { movieEntyDao.deleteAll() }
}
