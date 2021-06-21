package org.example.moviesapp.viewmodel

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.example.moviesapp.repo.IRepository
import org.example.moviesapp.room.MovieEnty
import org.example.moviesapp.room.MovieEntyRoomDatabase
import org.example.moviesapp.room.MovieEntyRoomRepository
import org.example.moviesapp.utils.Constants
import org.example.moviesapp.utils.U
import org.example.moviesapp.utils.formatter.IFormatter
import org.example.moviesapp.viewmodel.states.TaskUiState


class MainViewModel(
        application: Application,
        private val repo: IRepository,
        private val formatter: IFormatter
) : ViewModel() {

    private val tag = javaClass.simpleName

    private var roomRepository: MovieEntyRoomRepository

    private var jobTopRated: Job? = null

    private var jobSearch: Job? = null

    private var _movieEnties: MutableLiveData<List<MovieEnty>> = MutableLiveData()

    var movieEnties: LiveData<List<MovieEnty>> = _movieEnties

    //<editor-fold desc="TASK UI STATE">
    var taskUiState: MutableLiveData<TaskUiState> = MutableLiveData()

    private fun updateUiError(msg: String?) {
        taskUiState.postValue(TaskUiState.Error(msg))
    }

    private fun updateUiLoadingStart() {
        taskUiState.postValue(TaskUiState.LoadingStart)
    }

    private fun updateUiLoadingEnd() {
        taskUiState.postValue(TaskUiState.LoadingEnd)
    }
    //</editor-fold>

    init {
        val myEntyDao = MovieEntyRoomDatabase
                .getDatabase(application, viewModelScope)
                .entyDao()
        roomRepository = MovieEntyRoomRepository(myEntyDao)

        movieEnties = roomRepository.allMovieEnties

        fetchTopRated()
    }

    @CallSuper
    override fun onCleared() {
        jobTopRated?.cancel()
        jobSearch?.cancel()
        super.onCleared()
    }


    fun fetchTopRated() {
        updateUiLoadingStart()
        jobTopRated = viewModelScope.launch(Dispatchers.IO) {
            try {
                val dataStringJson: String? = repo.fetchMovies(Constants.fullUrlTopRated)
                updateUiLoadingEnd()
                handleDataResponse(dataStringJson)
            } catch (e: Exception) {
                updateUiError("ERR $e")
            }
        }
    }

    fun fetchBySearch(titleSearch: String) {
        updateUiLoadingStart()
        jobSearch = viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = Constants.fullUrlSearchByTitle  + titleSearch
                val dataStringJson: String? = repo.fetchMovies(url)
                updateUiLoadingEnd()
                handleDataResponse(dataStringJson)
            } catch (e: Exception) {
                updateUiError("ERR $e")
            }
        }
    }


    private suspend fun handleDataResponse(dataStringJson: String?) {
        if (dataStringJson != null) {
            U.l(tag, "stringJson $dataStringJson")
            val transformedItems: List<MovieEnty> =
                formatter.formatDataToListMovieEnty(dataStringJson)
            if (transformedItems == null || transformedItems.isEmpty()) {
                U.l(tag, "Response list is empty")
                updateUiError("Response list is empty." +
                        "\n\nAre you searching?\nPlease, try with another search")
            } else {
                deleteAndInsertToRoom(transformedItems)
            }
        } else {
            updateUiError("server response is null.\nDo you have Internet?")
        }
    }

    private suspend fun deleteAndInsertToRoom(transformedItems: List<MovieEnty>) {
        try {
            U.l(tag, "deleteAll & insert")
            roomRepository.deleteAll()

            for (result in transformedItems) {
                roomRepository.insert(
                    MovieEnty(
                        result.originalTitle,
                        result.posterPath,
                        result.genre,
                        result.releaseDate,
                        result.overview,
                        result.originalLanguage
                    )
                )
            }

        } catch (e: Exception) {
            updateUiError("ERROR deleteAll & insertToRoom")
        }
    }

    fun deleteAllAsync() = viewModelScope.launch(Dispatchers.IO) {
        roomRepository.deleteAll()
    }
}