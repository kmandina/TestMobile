package com.kmandina.testmobile.data.api.serialize

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MoviesRequest {

    @SerializedName("movies")
    @Expose
    var movies: List<MoviesSerialize>? = null

    @SerializedName("routes")
    @Expose
    var routes: List<RoutesSerialize>? = null
}

class RoutesSerialize{

    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("sizes")
    @Expose
    var sizes: SizesSerialize? = null

}

class SizesSerialize {

    @SerializedName("large")
    @Expose
    var large: String? = null

    @SerializedName("medium")
    @Expose
    var medium: String? = null

    @SerializedName("small")
    @Expose
    var small: String? = null

    @SerializedName("x-large")
    @Expose
    var xLarge: String? = null

}

class MoviesSerialize {

    @SerializedName("id")
    @Expose
    var id: Long? = null

    @SerializedName("rating")
    @Expose
    var rating: String? = null

    @SerializedName("position")
    @Expose
    var position: Int? = null

    @SerializedName("genre")
    @Expose
    var genre: String? = null

    @SerializedName("synopsis")
    @Expose
    var synopsis: String? = null

    @SerializedName("length")
    @Expose
    var length: String? = null

    @SerializedName("release_date")
    @Expose
    var release_date: String? = null

    @SerializedName("distributor")
    @Expose
    var distributor: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("original_name")
    @Expose
    var original_name: String? = null

    @SerializedName("media")
    @Expose
    var media: List<MediaSerialize>? = null

    @SerializedName("cast")
    @Expose
    var cast: List<CastSerialize>? = null

    @SerializedName("cinemas")
    @Expose
    var cinemas: Array<String>? = null

    @SerializedName("categories")
    @Expose
    var categories: Array<String>? = null
}

class MediaSerialize {

    @SerializedName("resource")
    @Expose
    var resource: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null

}

class CastSerialize {

    @SerializedName("label")
    @Expose
    var label: String? = null

    @SerializedName("media")
    @Expose
    var values: Array<String>? = null

}
