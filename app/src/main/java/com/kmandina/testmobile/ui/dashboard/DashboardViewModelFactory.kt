package com.kmandina.testmobile.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kmandina.testmobile.data.model.MovieRespository

class DashboardViewModelFactory (
    private val repos: MovieRespository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(repos) as T
    }
}