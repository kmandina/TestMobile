package com.kmandina.testmobile.ui.profile

import android.content.Context
import android.text.Editable
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.kmandina.testmobile.data.model.UserRepository

class ProfileViewModel (
    private val repos: UserRepository
) : ViewModel() {

    val user = repos.getUser()

    fun update(context: Context, dialog: AlertDialog?) = repos.updateUser(context, dialog)

    fun trx(context: Context, dialog: AlertDialog?, cardNumber: String) = repos.trx(context, dialog, cardNumber)
}