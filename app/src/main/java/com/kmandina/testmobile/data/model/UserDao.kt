package com.kmandina.testmobile.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * from user ORDER BY id DESC")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Long): LiveData<User>

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): LiveData<User>

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByUsername(email: String): LiveData<User>

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE email = :email LIMIT 1)")
    suspend fun existUser(email: String): Boolean

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserExist(email: String): User

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}