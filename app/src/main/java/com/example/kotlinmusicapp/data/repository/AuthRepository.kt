package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.UserPreferences
import com.example.kotlinmusicapp.data.network.AuthApi

class  AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(token: String){

        preferences.saveAccessTokens(token,"")

    }

}