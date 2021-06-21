package org.example.moviesapp

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import org.example.moviesapp.repo.MoviesHttpRepo
import org.example.moviesapp.ui.UiMyItemLibActivity
import org.example.moviesapp.utils.formatter.Formatter
import org.example.moviesapp.viewmodel.BaseVMProviderFactory
import org.example.moviesapp.viewmodel.MainViewModel
import org.example.moviesapp.viewmodel.states.TaskUiState


class MainActivity : UiMyItemLibActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAndObserveViewModel()
        isBadInternetShowMessage()
    }

    private fun initAndObserveViewModel(){
        viewModel = ViewModelProviders.of(this,
                BaseVMProviderFactory(application, MoviesHttpRepo(), Formatter()))
            .get(MainViewModel::class.java)

        viewModel.movieEnties.observe(this, { movieEnties ->
            movieEnties?.let {
                updateUiList(movieEnties)
            }
        })

        viewModel.taskUiState.observe(this, { state ->
            state?.let {
                updateUiState(state)
            }
        })
    }
    override fun clearRecycler() {
        viewModel.deleteAllAsync()
    }

    override fun fetchTopRated() {
        l("fetchTopRated")
        viewModel.fetchTopRated()
    }

    override fun fetchBySearch(titleSearch: String) {
        l("fetchBySearch $titleSearch")
        viewModel.fetchBySearch(titleSearch)
    }

    private fun updateUiState(state: TaskUiState) {
        l("updateUiState")
        when (state) {
            TaskUiState.LoadingStart -> updateUiLoadingStart()
            TaskUiState.LoadingEnd -> updateUiLoadingEnd()
            is TaskUiState.Error -> updateUiError(state.msg)
        }.exhaustive
    }

    private val <T> T.exhaustive: T get() = this
}