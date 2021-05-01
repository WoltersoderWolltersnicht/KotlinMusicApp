package com.example.kotlinmusicapp.repository

import android.provider.ContactsContract
import com.example.kotlinmusicapp.network.AuthApi

class  AuthRepository(
    private val api: AuthApi
) : BaseRepository(){

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

}