package com.example.kotlinmusicapp.data.network.apis

import com.example.kotlinmusicapp.data.responses.RegisterResponse
import com.example.kotlinmusicapp.data.responses.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UploadApi {
    @Multipart
    @POST("upload.php")
    suspend fun upload(
        @Part song : MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("artist") artist: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("private") private: RequestBody,
        @Part("user") user: RequestBody,
        ): UploadResponse

    @Multipart
    @POST("uploadImg.php")
    suspend fun uploadSong(
        @Part image : MultipartBody.Part,
        @Part("song_id") song_id: RequestBody

    ): RegisterResponse
}