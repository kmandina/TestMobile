package com.kmandina.testmobile.data.model

import android.content.Context
import com.kmandina.testmobile.utils.MyUtils

class RouteRepository private constructor(
    private val sizeDao: SizeDao,
    private val routeDao: RouteDao) {

    fun getAllRoute() = routeDao.getAllRouteSize()

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: RouteRepository? = null

        fun getInstance(sizeDao: SizeDao, routeDao: RouteDao) =
            instance ?: synchronized(this) {
                instance
                    ?: RouteRepository(sizeDao, routeDao).also { instance = it }
            }
    }
}