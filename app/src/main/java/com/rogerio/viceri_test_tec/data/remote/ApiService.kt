package com.rogerio.viceri_test_tec.data.remote

import com.rogerio.viceri_test_tec.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}
