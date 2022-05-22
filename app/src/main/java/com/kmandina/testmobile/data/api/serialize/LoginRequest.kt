package com.kmandina.testmobile.data.api.serialize

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @field:SerializedName(
        "username"
    ) @field:Expose var username: String,

    @field:SerializedName(
        "password"
    ) @field:Expose var password: String,

    @field:SerializedName(
        "country_code"
    ) @field:Expose var countryCode: String,

    @field:SerializedName(
        "grant_type"
    ) @field:Expose var grantType: String,

    @field:SerializedName(
        "client_id"
    ) @field:Expose var clientId: String,

    @field:SerializedName(
        "client_secret"
    ) @field:Expose var clientSecret: String


)