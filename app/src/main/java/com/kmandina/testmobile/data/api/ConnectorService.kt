package com.kmandina.testmobile.data.api

import com.kmandina.testmobile.data.api.serialize.LoginRequest
import com.kmandina.testmobile.data.api.serialize.LoginResponse
import com.kmandina.testmobile.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ConnectorService {

    @POST("/v2/oauth/token")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: no-cache",
        "HOST: stage-api.cinepolis.com",
        "api_key: stage_HNYh3RaK_Test",
        "POST: /v2/oauth/token HTTP/1.1"
    )
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("https://stage-api.cinepolis.com/v1/members/profile?country_code=MX")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: no-cache",
        "HOST: stage-api.cinepolis.com",
        "api_key: stage_HNYh3RaK_Test",
        "GET: /v1/members/profile?country_code=MX HTTP/1.1"
    )
    fun getUser(): Call<User?>

    @GET("https://stage-api.cinepolis.com/v2/locations/cinemas?cities=61&country_code=MX&include_cinemas=true")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: no-cache",
        "HOST: stage-api.cinepolis.com",
        "api_key: stage_HNYh3RaK_Test",
        "GET: /v2/movies?country_code=MX&amp;cinemas=61 HTTP/1.1"
    )
    fun getCinemas(): Call<User?>

}