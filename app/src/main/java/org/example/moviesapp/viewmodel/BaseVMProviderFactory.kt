package org.example.moviesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.moviesapp.repo.IRepository
import org.example.moviesapp.utils.formatter.IFormatter


class BaseVMProviderFactory(
    private val application: Application,
    private val repo: IRepository,
    private val formatter: IFormatter
): ViewModelProvider.Factory  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, repo, formatter) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}