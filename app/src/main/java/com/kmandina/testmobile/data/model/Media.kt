package com.kmandina.testmobile.data.model

import androidx.room.*

@Entity(
    tableName = "media",
    foreignKeys = [
        ForeignKey(entity = Movie::class, parentColumns = ["id"], childColumns = ["movie_id"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    ],
    indices = [Index("movie_id")]
)
data class Media(

    @ColumnInfo(name = "resource") val resource: String,

    @ColumnInfo(name = "type") val type: String,

    @ColumnInfo(name = "code") val code: String,

    @ColumnInfo(name = "movie_id") val movieId: Long

){
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
}