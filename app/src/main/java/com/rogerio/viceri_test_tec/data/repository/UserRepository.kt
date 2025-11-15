package com.rogerio.viceri_test_tec.data.repository

import android.util.Log
import com.rogerio.viceri_test_tec.data.local.UserDao
import com.rogerio.viceri_test_tec.data.local.UserEntity
import com.rogerio.viceri_test_tec.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService,
    private val dao: UserDao
) {

    fun getUsers(): Flow<List<UserEntity>> {
        return dao.getAll()
    }

    suspend fun fetchAndSaveUsers() {
        try {
            val users = api.getUsers() // Lista direta (NÃO é Response)

            val mappedUsers = users.map {
                UserEntity(
                    id = it.id,
                    name = it.name,
                    username = it.username,
                    email = it.email,
                    city = it.address?.city,
                    phone = it.phone,
                    website = it.website,
                    companyName = it.company?.name
                )
            }

            Log.i("TAG", "$$$$$$ - inseriu: " + mappedUsers)
            dao.insertAll(mappedUsers)

        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("TAG", "$$$$$$ - deu merda: " + e)
        }
    }

    suspend fun getUserById(id: Int): UserEntity? {
        return dao.getById(id)
    }

    fun observeUsers(): Flow<List<UserEntity>> = dao.getAll()

    fun search(query: String): Flow<List<UserEntity>> {
        val q = "%${query}%"
        return dao.search(q)
    }
}