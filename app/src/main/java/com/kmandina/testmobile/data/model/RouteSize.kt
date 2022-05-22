package com.kmandina.testmobile.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class RouteSize (

    @Embedded
    val route: Route,

    @Relation(parentColumn = "id", entityColumn = "route_id", entity = Size::class)
    val sizes: List<Size> = emptyList()

)