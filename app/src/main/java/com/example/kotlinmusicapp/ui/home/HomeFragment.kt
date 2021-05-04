package com.example.kotlinmusicapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.kotlinmusicapp.R
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.UserApi
import com.example.kotlinmusicapp.data.repository.UserRepository
import com.example.kotlinmusicapp.data.responses.User
import com.example.kotlinmusicapp.databinding.FragmentHomeBinding
import com.example.kotlinmusicapp.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner, Observer {

            when(it){
                is Resource.Success -> {
                    updateUI(it.value.user)
                }

                is Resource.Loading -> {

                    //Ocultar progressbar

                }
            }

        })

    }

    private fun updateUI(user: User){

        with(binding){
            text.text = user.id.toString() + "  " + user.email.toString()
        }

    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container,false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.accessToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java , token)

        return UserRepository(api)
    }
}