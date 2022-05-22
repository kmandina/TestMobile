package com.kmandina.testmobile.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RouteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(route: Route): Long

    @Query("SELECT * FROM route")
    fun getAllRoute(): LiveData<List<Route>>

    @Query("SELECT * FROM route WHERE id = :id")
    fun getRoute(id: Long): LiveData<Route>

    @Update
    fun updateRoute(route: Route)

    @Query("DELETE FROM route")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteRoute(route: Route)

    @Query("DELETE FROM route WHERE id =:id")
    suspend fun deleteRoutebyId(id: Long)

    @Transaction
    @Query("SELECT * FROM route WHERE id IN (SELECT route_id FROM size )")
    fun getAllRouteSize(): LiveData<List<RouteSize>>

    @Transaction
    @Query("SELECT * FROM route WHERE id IN (SELECT route_id FROM size )")
    suspend fun getAllRouteSizeSuspend(): List<RouteSize>

    @Query("SELECT EXISTS(SELECT 1 FROM route WHERE code = :code LIMIT 1)")
    suspend fun existRoute(code: String): Boolean

    @Query("SELECT * FROM route WHERE code = :code")
    suspend fun getRouteExist(code: String): Route
}