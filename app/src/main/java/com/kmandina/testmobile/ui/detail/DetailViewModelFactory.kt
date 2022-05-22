package com.kmandina.testmobile.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kmandina.testmobile.data.model.MovieRespository

class DetailViewModelFactory (
    private val movieRepository: MovieRespository,
    private val movieId: Long
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movieRepository, movieId) as T
    }
}