package com.example.kotlinmusicapp.ui.auth

import android.os.Bundle
import androidx.navigation.findNavController
import com.example.kotlinmusicapp.data.network.apis.AuthApi
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.ui.base.BaseActivity
import com.example.kotlinmusicapp.databinding.ActivityAuthBinding
import com.example.kotlinmusicapp.ui.auth.fragments.LoginFragmentDirections
import com.example.kotlinmusicapp.ui.auth.fragments.RegisterFragmentDirections
import com.example.kotlinmusicapp.ui.enable

class AuthActivity : BaseActivity<AuthViewModel, ActivityAuthBinding, AuthRepository>() {

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding.btnLogin.enable(false)
                binding.btnRegister.enable(true)

                binding.btnRegister.setOnClickListener{
                        binding.btnRegister.enable(false)
                        binding.btnLogin.enable(true)
                        val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
                        findNavController(binding.fragment.id).navigate(action)
                }

                binding.btnLogin.setOnClickListener{
                        binding.btnRegister.enable(true)
                        binding.btnLogin.enable(false)
                        val action = RegisterFragmentDirections.actionRegistrationFragmentToLoginFragment()
                        findNavController(binding.fragment.id).navigate(action)
                }
        }

        override fun getViewBinding() = ActivityAuthBinding.inflate(layoutInflater)

        override fun getViewModel() = AuthViewModel::class.java

        override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))

}