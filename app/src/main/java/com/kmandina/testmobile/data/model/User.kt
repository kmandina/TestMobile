package com.kmandina.testmobile.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(

    @field:SerializedName(
        "profile_picture"
    ) @field:Expose val pictureProfile: String,

    @field:SerializedName(
        "phone_number"
    ) @field:Expose val phoneNumber: String,

    @field:SerializedName(
        "email"
    ) @field:Expose  val email: String,

    @field:SerializedName(
        "first_name"
    ) @field:Expose val firstName: String,

    @field:SerializedName(
        "last_name"
    ) @field:Expose val lastName: String,

    @field:SerializedName(
        "card_number"
    ) @field:Expose val cardNumber: String
){
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
}