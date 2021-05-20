package com.example.kotlinmusicapp.data.responses

import com.example.kotlinmusicapp.data.responses.types.User

data class LoginResponse  (
    val status:StatusResponse,
    val user: User
)