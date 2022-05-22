package com.kmandina.testmobile.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class MovieMedia(

    @Embedded
    val movie: Movie,

    @Relation(parentColumn = "id", entityColumn = "movie_id", entity = Media::class)
    val medias: List<Media> = emptyList()

)