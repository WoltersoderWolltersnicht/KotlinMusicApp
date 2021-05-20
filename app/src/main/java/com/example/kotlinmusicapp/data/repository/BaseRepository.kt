package com.example.kotlinmusicapp.data.repository

import com.example.kotlinmusicapp.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

//Base Class for all Repositorys
abstract class BaseRepository{

    //Function ApiCall
    suspend fun <T> safeApiCall(
    //Needs an ApiCall
        apiCall:suspend () -> T
    //Returns an HTTP Resource
    ) : Resource<T>{
        //On ApiCall Response
        return withContext(Dispatchers.IO){
            try {
                //Success Resource
                Resource.Success(apiCall.invoke())
            }catch (throwable : Throwable){
                //Error Resource
                when(throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else ->
                        Resource.Failure(true, null, null)
                }
            }
        }
    }

}