package com.example.kotlinmusicapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.data.repository.BaseRepository
import com.example.kotlinmusicapp.ui.auth.AuthViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(

    private val repository: BaseRepository

) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository ) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}