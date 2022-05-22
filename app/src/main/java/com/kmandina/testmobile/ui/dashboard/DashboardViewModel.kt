package com.kmandina.testmobile.ui.dashboard

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.kmandina.testmobile.data.model.MovieRespository

class DashboardViewModel  (
    private val repos: MovieRespository,
) : ViewModel() {

    val movies = repos.getAllMovie()

    val routes = repos.getAllRoute()

    fun updateMovie(context: Context, dialog: AlertDialog?) = repos.insertAllMovie(context, dialog)

}