package com.kmandina.testmobile

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.kmandina.testmobile.data.api.ConnectorService
import com.kmandina.testmobile.data.api.serialize.LoginRequest
import com.kmandina.testmobile.data.api.serialize.LoginResponse
import com.kmandina.testmobile.databinding.ActivityLoginBinding
import com.kmandina.testmobile.utils.api
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity(), TextWatcher {

    private lateinit var binding: ActivityLoginBinding

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = PreferenceManager.getDefaultSharedPreferences(this)

        initViews(sp)
    }

    private fun initViews(sp: SharedPreferences) {

        binding.etUser.addTextChangedListener(this)
        binding.etPassword.addTextChangedListener(this)

        binding.loginAction.setOnClickListener {
            if (binding.layoutUser.error == null && binding.layoutPassword.error == null) {

                val user = binding.etUser.text.toString().lowercase()
                val pass = binding.etPassword.text.toString()

                val builder = OkHttpClient.Builder()
                val okHttpClient = builder
                    .readTimeout(180, TimeUnit.SECONDS)
                    .connectTimeout(180, TimeUnit.SECONDS)

                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(api)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val connectorService: ConnectorService =
                    retrofit.create(ConnectorService::class.java)

//                val loginRequest = LoginRequest(
//
//                )

                val call: Call<LoginResponse> =
                    connectorService.login(
                        user,
                        pass,
                        "MX",
                        "password",
                        "IATestCandidate",
                        "c840457e777b4fee9b510fbcd4985b68"
                    )

                if(dialog != null){
                    dialog!!.show()
                }

                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {

                        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()

                        if (response.isSuccessful) {

                            if (response.body()?.access != null) {
                                Log.d("Auth", "token: " + response.body()!!.access!!)
                                sp.edit().putString("token", response.body()!!.access!!).apply()
                                sp.edit().putString("refreshToken", response.body()!!.refresh!!)
                                    .apply()

                                sp.edit().putBoolean("login", true).apply()

                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                            } else {
                                Log.d("Auth", "error login")

                                AlertDialog.Builder(this@LoginActivity)
                                    .setTitle("Notification")
                                    .setMessage("Error on authentication")
                                    .setPositiveButton("Accept") { _, _ ->  }
                                    .show()

                            }


                        } else {
                            Log.d("Auth", "error login")

                            AlertDialog.Builder(this@LoginActivity)
                                .setTitle("Notification")
                                .setMessage("Error on authentication: ${response.errorBody()?.string()}")
                                .setPositiveButton("Accept") { _, _ -> }
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("Auth", "error connection")

                        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()

                        AlertDialog.Builder(this@LoginActivity)
                            .setTitle("Notification")
                            .setMessage("Error on authentication: ${t.message}")
                            .setPositiveButton("Accept") { _, _ -> }
                            .show()
                    }
                })

            }
        }

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        if (!TextUtils.isEmpty(binding.etUser.text) && !TextUtils.isEmpty(binding.layoutUser.error)) {
            binding.layoutUser.error = null
        }
        if (!TextUtils.isEmpty(binding.etPassword.text) && !TextUtils.isEmpty(binding.layoutPassword.error)) {
            binding.layoutPassword.error = null
        }

    }

}