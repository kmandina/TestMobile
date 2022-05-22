package com.kmandina.testmobile.data.api.serialize

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class TransactionRequest(

    @field:SerializedName(
        "card_number"
    ) @field:Expose var cardNumber: String,

    @field:SerializedName(
        "country_code"
    ) @field:Expose var countryCode: String,

    @field:SerializedName(
        "pin"
    ) @field:Expose var pin: String,

    @field:SerializedName(
        "transaction_include"
    ) @field:Expose var transactionInclude: Boolean,

)