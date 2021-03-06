package com.example.kotlinmusicapp.data.network

import com.example.kotlinmusicapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class  RemoteDataSource{

    companion object{
        //Base API URL
        private const val BASE_URL = "http://spotify.rottinghex.com/"
    }

    //Function that creates API Calls Returning an
    fun<Api> buildApi(
        api : Class<Api>,
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder()
                    //Add Header to Http Request
                    .addInterceptor { chain ->
                       chain.proceed(chain.request().newBuilder().also {
                       }.build())
                    }.also { client ->
                if (BuildConfig.DEBUG) {
                    val login = HttpLoggingInterceptor()
                    login.setLevel(HttpLoggingInterceptor.Level.BASIC)
                    client.addInterceptor(login)
                }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}