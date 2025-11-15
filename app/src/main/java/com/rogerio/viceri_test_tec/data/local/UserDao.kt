package com.rogerio.viceri_test_tec.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE name LIKE :q OR email LIKE :q ORDER BY name ASC")
    fun search(q: String): Flow<List<UserEntity>>
}