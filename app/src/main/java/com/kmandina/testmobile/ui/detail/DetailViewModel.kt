package com.kmandina.testmobile.ui.detail

import androidx.lifecycle.ViewModel
import com.kmandina.testmobile.data.model.MovieRespository

class DetailViewModel (
    private val repos: MovieRespository,
    private val movieId: Long
) : ViewModel() {

    val movie = repos.getMovie(movieId)

    val routes = repos.getAllRoute()

    suspend fun getTrailerPath() = repos.getTrailerPath(movieId)
}