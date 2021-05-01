package com.example.kotlinmusicapp.network

import com.example.kotlinmusicapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class  RemoteDataSource{

    companion object{
        private const val BASE_URL = "http://10.0.2.2/projects/userapp/";
    }

    fun<Api> buildApi(
        api : Class<Api>
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                if (BuildConfig.DEBUG) {
                    val loggin = HttpLoggingInterceptor();
                    loggin.setLevel(HttpLoggingInterceptor.Level.BASIC)
                    client.addInterceptor(loggin)
                }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    

}