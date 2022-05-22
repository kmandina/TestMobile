package com.kmandina.testmobile.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route")
data class Route(
    @ColumnInfo(name = "code") val code: String
){
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
}