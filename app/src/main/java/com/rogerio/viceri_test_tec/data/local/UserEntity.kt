package com.rogerio.viceri_test_tec.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val username: String?,
    val email: String?,
    val city: String?,
    val phone: String?,
    val website: String?,
    val companyName: String?
)
