package com.kmandina.testmobile.utils

import android.content.Context
import com.kmandina.testmobile.data.model.AppDatabase
import com.kmandina.testmobile.data.model.MovieRespository
import com.kmandina.testmobile.data.model.UserRepository
import com.kmandina.testmobile.ui.dashboard.DashboardViewModelFactory
import com.kmandina.testmobile.ui.profile.ProfileViewModelFactory

object InjectorUtils {

    private fun getUserRepository(context: Context): UserRepository {
        return UserRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).userDao(),
        )
    }

    private fun getMovieRepository(context: Context): MovieRespository {
        return MovieRespository.getInstance(
            AppDatabase.getInstance(context.applicationContext).mediaDao(),
            AppDatabase.getInstance(context.applicationContext).movieDao(),
            AppDatabase.getInstance(context.applicationContext).sizeDao(),
            AppDatabase.getInstance(context.applicationContext).routeDao(),
        )
    }

    fun provideUserViewModelFactory(
        context: Context
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(getUserRepository(context))
    }

    fun provideDashboardViewModelFactory(
        context: Context
    ): DashboardViewModelFactory {
        return DashboardViewModelFactory(getMovieRepository(context))
    }

}
