package com.kmandina.testmobile.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie): Long

    @Query("SELECT * FROM movie")
    fun getAllMovie(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovie(id: String): LiveData<Movie>

    @Update
    fun updateMovie(movie: Movie)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id =:id")
    suspend fun deleteMoviebyId(id: String)

    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (SELECT movie_id FROM media )")
    fun getAllMovieMedia(): LiveData<List<MovieMedia>>
}