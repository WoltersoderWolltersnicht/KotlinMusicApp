package com.example.kotlinmusicapp.data.repository
import com.example.kotlinmusicapp.data.network.apis.AuthApi

//Authentication Repository for handle API and DB calls
class  AuthRepository(private val api: AuthApi) : BaseRepository(){}