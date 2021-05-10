package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.network.apis.LoginApi

class LoginRepository(
    private val api : LoginApi
) : BaseRepository() {

    //Repository Function To call API login Function
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

}