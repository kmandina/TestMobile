package com.kmandina.testmobile.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kmandina.testmobile.data.api.serialize.*
import com.kmandina.testmobile.data.model.User
import retrofit2.Call
import retrofit2.http.*

interface ConnectorService {

    @POST("/v2/oauth/token")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "Accept: */*",
        "Accept-Encoding: gzip, deflate, br",
        "api_key: stage_HNYh3RaK_Test",
        "Cache-Control: no-cache",
        "Host: stage-api.cinepolis.com",
        "POST: /v2/oauth/token HTTP/1.1"
    )
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,

        @Field("password") password: String,

        @Field("country_code") countryCode: String,

        @Field("grant_type") grantType: String,

        @Field("client_id") clientId: String,

        @Field("client_secret") clientSecret: String

    ): Call<LoginResponse>

    @GET("/v1/members/profile?country_code=MX")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: no-cache",
        "HOST: stage-api.cinepolis.com",
        "api_key: stage_HNYh3RaK_Test",
        "GET: /v1/members/profile?country_code=MX HTTP/1.1"
    )
    fun getUser(): Call<User?>

    @GET("/v2/movies?country_code=MX&cinemas=61")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: no-cache",
        "HOST: stage-api.cinepolis.com",
        "api_key: stage_HNYh3RaK_Test",
        "GET: /v2/movies?country_code=MX&amp;cinemas=61 HTTP/1.1"
    )
    fun getMovies(): Call<MoviesRequest?>

    @POST("/v2/members/loyalty/")
    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: no-cache",
        "HOST: stage-api.cinepolis.com",
        "api_key: stage_4V78Fwm_android",
        "GET: /v2/members/loyalty/ HTTP/1.1"
    )
    fun transactions(@Body request: TransactionRequest): Call<TransactionResponse>
}