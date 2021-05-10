package com.example.kotlinmusicapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmusicapp.data.repository.*
import com.example.kotlinmusicapp.ui.auth.AuthViewModel
import com.example.kotlinmusicapp.ui.auth.fragments.LoginViewModel
import com.example.kotlinmusicapp.ui.auth.fragments.RegisterViewModel
import com.example.kotlinmusicapp.ui.home.MysongViewModel
import java.lang.IllegalArgumentException

//VM Factory Class that creates VM Instances
class ViewModelFactory(
    private val repository: BaseRepository

) : ViewModelProvider.NewInstanceFactory(){

    //Function that creates whanted VM Instance
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository ) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository as LoginRepository ) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(repository as RegisterRepository ) as T
            modelClass.isAssignableFrom(MysongViewModel::class.java) -> MysongViewModel(repository as MysongRepository ) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}