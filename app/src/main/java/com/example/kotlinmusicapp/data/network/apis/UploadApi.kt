package com.example.kotlinmusicapp.data.network.apis

import com.example.kotlinmusicapp.data.responses.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UploadApi {
    @Multipart
    @POST("upload.php")
    suspend fun upload(
        @Part image : MultipartBody.Part,
        @Part song : MultipartBody.Part,
        @Part("name") name: RequestBody

    ): RegisterResponse

    @Multipart
    @POST("upload.php")
    suspend fun uploadSong(
        @Part song : MultipartBody.Part,
        @Part("name") name: RequestBody

    ): RegisterResponse
}