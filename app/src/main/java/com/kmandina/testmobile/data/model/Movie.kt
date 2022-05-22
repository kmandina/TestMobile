package com.kmandina.testmobile.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie")
class Movie(

    @PrimaryKey @ColumnInfo(name = "id") val id: Long,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "rating") val rating: String,

    @ColumnInfo(name = "position") val position: Int,

    @ColumnInfo(name = "genre") val genre: String,

    @ColumnInfo(name = "synopsis") val synopsis: String,

    @ColumnInfo(name = "length") val length: String,

    @ColumnInfo(name = "release_date") val release_date: String,

    @ColumnInfo(name = "distributor") val distributor: String,

    @ColumnInfo(name = "code") val code: String,

    @ColumnInfo(name = "original_name") val original_name: String,

)