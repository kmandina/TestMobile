package com.kmandina.testmobile.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(media: Media): Long

    @Query("SELECT * FROM media")
    fun getAllMedia(): LiveData<List<Media>>

    @Query("SELECT * FROM media WHERE id = :id")
    fun getMedia(id: String): LiveData<Media>

    @Update
    fun updateMedia(media: Media)

    @Query("DELETE FROM media")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMedia(media: Media)

    @Query("DELETE FROM media WHERE id =:id")
    suspend fun deleteMediabyId(id: String)

}