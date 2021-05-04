package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.UserPreferences
import com.example.kotlinmusicapp.data.network.AuthApi
import com.example.kotlinmusicapp.data.network.UserApi

class  UserRepository(
    private val api: UserApi,
) : BaseRepository(){

    suspend fun getUser() = safeApiCall {
        api.getUser()
    }

}