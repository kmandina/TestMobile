package com.kmandina.testmobile.data.model

import androidx.room.*

@Entity(
    tableName = "size",
    foreignKeys = [
        ForeignKey(entity = Route::class, parentColumns = ["id"], childColumns = ["route_id"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    ],
    indices = [Index("route_id")]
)
data class Size(
    @ColumnInfo(name = "code") val large: String = "",

    @ColumnInfo(name = "medium") val medium: String = "",

    @ColumnInfo(name = "small") val small: String = "",

    @ColumnInfo(name = "xLarge") val xLarge: String = "",

    @ColumnInfo(name = "route_id") val routeId: Long
){
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
}