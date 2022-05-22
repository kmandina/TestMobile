package com.kmandina.testmobile.data.model

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.room.ColumnInfo
import com.kmandina.testmobile.data.api.ConnectorService
import com.kmandina.testmobile.data.api.serialize.MoviesRequest
import com.kmandina.testmobile.utils.MyUtils
import com.kmandina.testmobile.utils.MyUtils.putValueString
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

class MovieRespository private constructor(
    private val mediaDao: MediaDao,
    private val movieDao: MovieDao,
    private val sizeDao: SizeDao,
    private val routeDao: RouteDao)  {

    fun getAllMovie() = movieDao.getAllMovieMedia()

    fun getAllRoute() = routeDao.getAllRouteSize()

    fun getMovie(id: Long) = movieDao.getMovieMedia(id)

    fun insertAllMovie(context: Context, dialog: AlertDialog?) {

        if(MyUtils.isNetworkConnected(context)) {

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

            val getMoviesRequest = apiService.getMovies()

            dialog?.show()

            getMoviesRequest.enqueue(object : Callback<MoviesRequest?> {
                override fun onResponse(call: Call<MoviesRequest?>, response: Response<MoviesRequest?>) {
                    if (response.isSuccessful && response.body() != null) {

                        if (dialog != null && dialog.isShowing) dialog.dismiss()

                        CoroutineScope(Dispatchers.IO).launch {

                            if(response.body()!!.routes != null) {

                                for (route in response.body()!!.routes!!) {

                                    if (routeDao.existRoute(route.code!!)) {

                                        val routeR = routeDao.getRouteExist(route.code!!)

                                        if(route.sizes != null){

                                            if(sizeDao.existSize(routeR.id)){
                                                val sizeS = sizeDao.getSizeExist(routeR.id)

                                                val sizeM = Size(
                                                    large  = putValueString(route.sizes!!.large),

                                                    medium  = putValueString(route.sizes!!.medium),

                                                    small  = putValueString(route.sizes!!.small),

                                                    xLarge  = putValueString(route.sizes!!.xLarge),

                                                    routeId = routeR.id
                                                )

                                                if(sizeS != sizeM){
                                                    sizeDao.updateSize(sizeM)
                                                }

                                            }else{

                                                sizeDao.insert(
                                                    Size(
                                                        large  = putValueString(route.sizes!!.large),

                                                        medium  = putValueString(route.sizes!!.medium),

                                                        small  = putValueString(route.sizes!!.small),

                                                        xLarge  = putValueString(route.sizes!!.xLarge),

                                                        routeId = routeR.id
                                                    )
                                                )

                                            }

                                        }

                                    }else{
                                        val id = routeDao.insert(
                                            Route(
                                                code = route.code!!,
                                            )
                                        )

                                        sizeDao.insert(
                                            Size(
                                                large  = putValueString(route.sizes!!.large),

                                                medium  = putValueString(route.sizes!!.medium),

                                                small  = putValueString(route.sizes!!.small),

                                                xLarge  = putValueString(route.sizes!!.xLarge),

                                                routeId = id
                                            )
                                        )
                                    }


                                }

                            }

                            if(response.body()!!.movies != null) {

                                for (movie in response.body()!!.movies!!) {

                                    if (movieDao.existMovie(movie.id!!.toLong())) {

                                        val mov = movieDao.getMovieExist(movie.id!!.toLong())

                                        val movMovie = Movie(
                                            id = movie.id!!.toLong(),
                                            name = movie.name!!,
                                            rating = movie.rating!!,
                                            position = movie.position!!,
                                            genre = movie.genre!!,
                                            synopsis = movie.synopsis!!,
                                            length = movie.length!!,
                                            release_date = movie.release_date!!,
                                            distributor = movie.distributor!!,
                                            code = movie.code!!,
                                            original_name = movie.original_name!!,
                                        )

                                        if(mov != movMovie){
                                            movieDao.updateMovie(movMovie)
                                        }

                                        if(!movie.media.isNullOrEmpty()){

                                            for(media in movie.media!!){

                                                if (mediaDao.existMedia(media.resource!!)) {

                                                    val med = mediaDao.getMediaExist(media.resource!!)

                                                    val medMedia =  Media(
                                                        resource = media.resource!!,
                                                        type = media.type!!,
                                                        code = media.code!!,
                                                        movieId = movie.id!!.toLong(),
                                                    )

                                                    if(med != medMedia){
                                                        mediaDao.updateMedia(medMedia)
                                                    }


                                                }else{
                                                    mediaDao.insert(
                                                        Media(
                                                            resource = media.resource!!,
                                                            type = media.type!!,
                                                            code = media.code!!,
                                                            movieId = movie.id!!.toLong(),
                                                        )
                                                    )
                                                }

                                            }

                                        }

                                    }else{

                                        movieDao.insert(
                                            Movie(
                                                id = movie.id!!.toLong(),
                                                name = movie.name!!,
                                                rating = movie.rating!!,
                                                position = movie.position!!,
                                                genre = movie.genre!!,
                                                synopsis = movie.synopsis!!,
                                                length = movie.length!!,
                                                release_date = movie.release_date!!,
                                                distributor = movie.distributor!!,
                                                code = movie.code!!,
                                                original_name = movie.original_name!!,
                                            )
                                        )

                                        if(!movie.media.isNullOrEmpty()){

                                            for(media in movie.media!!){

                                                mediaDao.insert(
                                                    Media(
                                                        resource = media.resource!!,
                                                        type  = media.type!!,
                                                        code  = media.code!!,
                                                        movieId = movie.id!!.toLong(),
                                                    )
                                                )

                                            }

                                        }

                                    }

                                }
                            }



                        }


                    }

                }

                override fun onFailure(call: Call<MoviesRequest?>, t: Throwable) {
                    Log.d("getMovies", "isNotSuccessful")

                    if (dialog != null && dialog.isShowing) dialog.dismiss()
                }

            })
        }

    }

    suspend fun getTrailerPath(movieId: Long): String {

        val routes = routeDao.getAllRouteSizeSuspend()

        val movie = movieDao.getMovieMediaSuspend(movieId)

        if (movie.medias.isNotEmpty()) {
            for (media in movie.medias) {
                if (media.code == "trailer_mp4") {
                    if (routes.isNotEmpty()) {
                        for (route in routes) {
                            if (route.route.code == "trailer_mp4") {
                                if (route.sizes.isNotEmpty()) {
                                    for (size in route.sizes) {
                                        if (size.medium != "") {
                                            Log.d("PATH", size.medium + media.resource)
                                            return size.medium + media.resource
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return ""
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: MovieRespository? = null

        fun getInstance(mediaDao: MediaDao,
                        movieDao: MovieDao,
                        sizeDao: SizeDao,
                        routeDao: RouteDao
        ) =
            instance ?: synchronized(this) {
                instance
                    ?: MovieRespository(mediaDao, movieDao, sizeDao, routeDao).also { instance = it }
            }
    }
}