package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.network.apis.RegisterApi

class RegisterRepository (
    private val api: RegisterApi
        ) : BaseRepository() {

    //Repository Function To call API login Function
    suspend fun register(
        name: String,
        email: String,
        password: String
    ) = safeApiCall {
        api.register(name,email, password)
    }

}