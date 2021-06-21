package org.example.moviesapp.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieEntyDao {

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(enty: MovieEnty)

    @WorkerThread
    @Query("DELETE FROM movie_enty_table")
    fun deleteAll()

    @WorkerThread
    @Query("SELECT * from movie_enty_table ORDER BY originalTitle ASC")
    fun getAllOrderByName(): LiveData<List<MovieEnty>>
}
