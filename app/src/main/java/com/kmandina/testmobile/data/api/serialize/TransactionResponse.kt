package com.kmandina.testmobile.data.api.serialize

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TransactionResponse {

    @SerializedName("transactions")
    @Expose
    var transactions: List<TX>? = null

}

class TX{

    @SerializedName("cinema")
    @Expose
    var cinema: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("points")
    @Expose
    var points: Float? = null

}