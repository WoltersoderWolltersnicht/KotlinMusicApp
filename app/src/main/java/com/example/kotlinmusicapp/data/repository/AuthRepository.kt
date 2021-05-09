package com.example.kotlinmusicapp.data.repository
import com.example.kotlinmusicapp.data.network.apis.AuthApi

//Authentication Repository for handle API and DB calls
class  AuthRepository(
    // AuthAoi Calls
    private val api: AuthApi,
) : BaseRepository(){

    //Repository Function To call API login Function
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    //Repository Function To call API login Function
    suspend fun register(
        name: String,
        email: String,
        password: String
    ) = safeApiCall {
        api.register(name,email, password)
    }

}