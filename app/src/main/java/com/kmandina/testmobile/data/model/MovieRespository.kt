package com.kmandina.testmobile.data.model

class MovieRespository private constructor(
    private val mediaDao: MediaDao,
    private val movieDao: MovieDao) {



    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: MovieRespository? = null

        fun getInstance(mediaDao: MediaDao, movieDao: MovieDao) =
            instance ?: synchronized(this) {
                instance
                    ?: MovieRespository(mediaDao, movieDao).also { instance = it }
            }
    }
}