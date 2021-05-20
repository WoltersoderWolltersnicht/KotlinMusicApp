package com.example.kotlinmusicapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmusicapp.data.repository.*
import com.example.kotlinmusicapp.ui.auth.AuthViewModel
import com.example.kotlinmusicapp.ui.auth.fragments.LoginViewModel
import com.example.kotlinmusicapp.ui.auth.fragments.RegisterViewModel
import com.example.kotlinmusicapp.ui.home.HomeViewModel
import com.example.kotlinmusicapp.ui.home.fragments.MysongViewModel
import com.example.kotlinmusicapp.ui.home.fragments.SongsViewModel
import com.example.kotlinmusicapp.ui.player.SongPlayerViewModel
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
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as HomeRepository ) as T
            modelClass.isAssignableFrom(MysongViewModel::class.java) -> MysongViewModel(repository as MysongRepository ) as T
            modelClass.isAssignableFrom(SongsViewModel::class.java) -> SongsViewModel(repository as SongsRepository ) as T
            modelClass.isAssignableFrom(SongPlayerViewModel::class.java) -> SongPlayerViewModel(repository as SongPlayerRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}