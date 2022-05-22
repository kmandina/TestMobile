package com.kmandina.testmobile.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmandina.testmobile.data.model.UserRepository

class ProfileViewModel (
    private val repos: UserRepository
) : ViewModel() {

    val user = repos.getUser()

    fun update(context: Context) = repos.updateUser(context)

}