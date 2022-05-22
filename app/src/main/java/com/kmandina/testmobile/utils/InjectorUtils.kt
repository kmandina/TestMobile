package com.kmandina.testmobile.utils

import android.content.Context
import com.kmandina.testmobile.data.model.AppDatabase
import com.kmandina.testmobile.data.model.UserRepository
import com.kmandina.testmobile.ui.profile.ProfileViewModelFactory

object InjectorUtils {

    private fun getUserRepository(context: Context): UserRepository {
        return UserRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).userDao(),
        )
    }

    fun provideUserViewModelFactory(
        context: Context
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(getUserRepository(context))
    }

}
