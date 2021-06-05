package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.network.apis.FavSongsApi
import com.example.kotlinmusicapp.data.network.apis.UploadApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadRepository (
    private val api : UploadApi,
) : BaseRepository () {

    suspend fun uploadSong(song: MultipartBody.Part,name: RequestBody) = safeApiCall { api.uploadSong(song,name) }

    suspend fun upload(img: MultipartBody.Part,song: MultipartBody.Part,name: RequestBody) = safeApiCall { api.upload(img,song,name) }

}