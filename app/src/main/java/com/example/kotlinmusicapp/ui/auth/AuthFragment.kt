package com.example.kotlinmusicapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.kotlinmusicapp.data.network.apis.AuthApi
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.databinding.FragmentAuthBinding
import com.example.kotlinmusicapp.ui.auth.fragments.LoginFragmentDirections
import com.example.kotlinmusicapp.ui.auth.fragments.RegisterFragmentDirections
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.enable
import com.example.kotlinmusicapp.ui.visible

class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnLogin.visible(false)
        binding.btnRegister.visible(true)

        binding.btnRegister.setOnClickListener{
            changeEnabled()
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            binding.fragmentAuth.findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener{
            changeEnabled();
            val action = RegisterFragmentDirections.actionRegistrationFragmentToLoginFragment("")
            binding.fragmentAuth.findNavController().navigate(action)
        }

    }

    //Returns Actual VM Class
    override fun getViewModel() = AuthViewModel::class.java

    //Returns Actual Fragment Binding
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthBinding = FragmentAuthBinding.inflate(inflater,container,false)

    //Returns Actual Fragment Repository
    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))


    //TODO : Find a better way to implement changes from child Fragments
    public fun changeEnabled(){

        if (binding.btnLogin.isVisible){
            binding.btnRegister.visible(true)
            binding.btnLogin.visible(false)
        }else{
            binding.btnRegister.visible(false)
            binding.btnLogin.visible(true)
        }

    }

}