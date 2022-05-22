package com.kmandina.testmobile.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SizeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(size: Size): Long

    @Query("SELECT * FROM size")
    fun getAllSize(): LiveData<List<Size>>

    @Query("SELECT * FROM size WHERE id = :id")
    fun getSize(id: String): LiveData<Size>

    @Update
    fun updateSize(size: Size)

    @Query("DELETE FROM size")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteSize(size: Size)

    @Query("DELETE FROM size WHERE id =:id")
    suspend fun deleteSizebyId(id: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM size WHERE route_id = :routeId LIMIT 1)")
    suspend fun existSize(routeId: Long): Boolean

    @Query("SELECT * FROM size WHERE route_id = :routeId")
    suspend fun getSizeExist(routeId: Long): Size
}