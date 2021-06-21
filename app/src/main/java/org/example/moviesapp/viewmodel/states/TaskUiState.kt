package org.example.moviesapp.viewmodel.states


sealed class TaskUiState {

    object LoadingStart : TaskUiState()

    object LoadingEnd : TaskUiState()

    data class Error(val msg: String?)  : TaskUiState()

}