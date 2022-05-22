package com.kmandina.testmobile.data.api.serialize

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("refresh_token")
    @Expose
    var refresh: String? = null

    @SerializedName("access_token")
    @Expose
    val access: String? = null

}