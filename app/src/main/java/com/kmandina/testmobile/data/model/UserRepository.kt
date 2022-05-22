package com.kmandina.testmobile.data.model

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.kmandina.testmobile.R
import com.kmandina.testmobile.data.api.ConnectorService
import com.kmandina.testmobile.data.api.serialize.TransactionRequest
import com.kmandina.testmobile.data.api.serialize.TransactionResponse
import com.kmandina.testmobile.ui.transaction.TrxAdapter
import com.kmandina.testmobile.utils.MyUtils
import com.kmandina.testmobile.utils.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserRepository private constructor(
    private val userDao: UserDao,
) {

    fun getUser() = userDao.getUser()

    fun getUserByUsername(user: String) = userDao.getUserByUsername(user)

    fun updateUser(context: Context, dialog: AlertDialog?){

        if(MyUtils.isNetworkConnected(context)) {

            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val token = sp.getString("token", "")!!

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS)
                .addNetworkInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "bearer $token")
                        .build()
                    chain.proceed(request)
                }
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(api)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService: ConnectorService = retrofit.create(ConnectorService::class.java)

            val user = apiService.getUser()

            dialog?.show()

            user.enqueue(object : Callback<User?> {
                override fun onResponse(call: Call<User?>, response: Response<User?>) {
                    if(response.isSuccessful && response.body() != null) {

                        if (dialog != null && dialog.isShowing) dialog.dismiss()

                        Log.d("getUser", "isSuccessful")

                        CoroutineScope(Dispatchers.IO).launch {

                            if(userDao.existUser(response.body()!!.email)) {

                                val us = userDao.getUserExist(response.body()!!.email)
                                val usR = User(
                                    pictureProfile = response.body()!!.pictureProfile,
                                    phoneNumber = response.body()!!.phoneNumber,
                                    email = response.body()!!.email,
                                    firstName = response.body()!!.firstName,
                                    lastName = response.body()!!.lastName,
                                    cardNumber = response.body()!!.cardNumber,
                                )

                                if(usR != us){
                                    userDao.updateUser(usR)
                                }

                            }else{
                                userDao.insert(
                                    User(
                                        pictureProfile = response.body()!!.pictureProfile,
                                        phoneNumber = response.body()!!.phoneNumber,
                                        email = response.body()!!.email,
                                        firstName = response.body()!!.firstName,
                                        lastName = response.body()!!.lastName,
                                        cardNumber = response.body()!!.cardNumber
                                    )
                                )
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    Log.d("getUser", "isNotSuccessful")
                    if (dialog != null && dialog.isShowing) dialog.dismiss()
                }

            })

        }

    }

    fun trx(context: Context, dialog: AlertDialog?, cardNumber: String){

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(api)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ConnectorService = retrofit.create(ConnectorService::class.java)

        val trax = apiService.transactions(
            TransactionRequest(
                cardNumber = cardNumber,
                countryCode = "MX",
                pin = "4804",
                transactionInclude = true
            )
        )

        dialog?.show()

        trax.enqueue(object : Callback<TransactionResponse?> {
            override fun onResponse(call: Call<TransactionResponse?>, response: Response<TransactionResponse?>) {
                if (response.isSuccessful && response.body() != null) {

                    if (dialog != null && dialog.isShowing) dialog.dismiss()

                    val d = Dialog(context)
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    d.setContentView(R.layout.trx_dialog)

                    val lp = WindowManager.LayoutParams()
                    lp.copyFrom(d.window!!.attributes)
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT
                    d.show()
                    d.window!!.attributes = lp

                    val recycler = d.findViewById<RecyclerView>(R.id.trxList)!!
                    val close = d.findViewById<ImageButton>(R.id.close)!!

                    val adapter = TrxAdapter()
                    recycler.adapter = adapter

                    if(response.body()!!.transactions != null){
                        adapter.submitList(response.body()!!.transactions!!)
                    }

                    close.setOnClickListener {
                        d.dismiss()
                    }

                }
            }

            override fun onFailure(call: Call<TransactionResponse?>, t: Throwable) {
                Log.d("getUser", "isNotSuccessful")
                if (dialog != null && dialog.isShowing) dialog.dismiss()
            }
        })


    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userDao: UserDao
        ) =

            instance ?: synchronized(this) {
                instance ?: UserRepository(
                    userDao
                ).also { instance = it }
            }
    }

}