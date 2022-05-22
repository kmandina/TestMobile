package com.kmandina.testmobile.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


object MyUtils {

    fun isNetworkConnected(context: Context): Boolean {

        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo

            networkInfo?.isConnected ?: false

        }
        else {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetwork

            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            networkCapabilities != null &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

    }

    fun putValueString(value: String?): String{
        return value ?: ""
    }

}
