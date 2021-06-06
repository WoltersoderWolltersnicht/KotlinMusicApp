package com.example.kotlinmusicapp.data.responses

import com.example.kotlinmusicapp.data.responses.types.User

data class UploadResponse  (
    val status:StatusResponse,
    val songId: String
)