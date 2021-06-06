package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.network.apis.FavSongsApi
import com.example.kotlinmusicapp.data.network.apis.UploadApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadRepository (
    private val api : UploadApi,
) : BaseRepository () {

    suspend fun uploadImage(image: MultipartBody.Part,songId: RequestBody) = safeApiCall { api.uploadSong(image,songId) }

    suspend fun upload(song: MultipartBody.Part,name: RequestBody,auth: RequestBody,gender: RequestBody,privacy: RequestBody,userId: RequestBody) = safeApiCall { api.upload(song,name,auth,gender,privacy,userId) }

}