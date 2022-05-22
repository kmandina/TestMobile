package com.kmandina.testmobile.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmandina.testmobile.data.model.MovieRespository
import com.kmandina.testmobile.data.model.RouteRepository

class DashboardViewModel  (
    private val repos: MovieRespository,
) : ViewModel() {

    val movies = repos.getAllMovie()

    val routes = repos.getAllRoute()

    fun updateMovie(context: Context) = repos.insertAllMovie(context)

}