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
    fun getMovie(id: Long): LiveData<Movie>

    @Transaction
    @Query("SELECT * FROM movie WHERE id = :id AND id IN (SELECT movie_id FROM media )")
    fun getMovieMedia(id: Long): LiveData<MovieMedia>

    @Transaction
    @Query("SELECT * FROM movie WHERE id = :id AND id IN (SELECT movie_id FROM media )")
    suspend fun getMovieMediaSuspend(id: Long): MovieMedia

    @Update
    fun updateMovie(movie: Movie)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id =:id")
    suspend fun deleteMoviebyId(id: Long)

    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (SELECT movie_id FROM media )")
    fun getAllMovieMedia(): LiveData<List<MovieMedia>>

    @Query("SELECT EXISTS(SELECT 1 FROM movie WHERE id = :id LIMIT 1)")
    suspend fun existMovie(id: Long): Boolean

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieExist(id: Long): Movie

//    @Query("DELETE FROM movie WHERE count <= :updateControl")
//    suspend fun deleteMovieUpdateControl(updateControl: Int)
//
//    @Transaction
//    @Query("SELECT * FROM movie WHERE id = :id AND (id IN (SELECT trip_id FROM address))")
//    suspend fun getMovieAddressSuspend(id: Long): TripAddresses?
}