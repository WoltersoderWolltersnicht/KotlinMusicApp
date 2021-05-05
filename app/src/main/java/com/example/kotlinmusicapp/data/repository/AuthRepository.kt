package com.example.kotlinmusicapp.data.repository
import com.example.kotlinmusicapp.data.network.AuthApi

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

}