package com.example.kotlinmusicapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.kotlinmusicapp.R
import com.example.kotlinmusicapp.databinding.FragmentLoginBinding
import com.example.kotlinmusicapp.network.AuthApi
import com.example.kotlinmusicapp.network.Resource
import com.example.kotlinmusicapp.repository.AuthRepository
import com.example.kotlinmusicapp.ui.base.BaseFragment

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                    Log.e("Login","Success")
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Login Failure", Toast.LENGTH_SHORT).show()
                    Log.e("Login","Login Failure")
                }
            }
        })

        binding.buttonLogin.setOnClickListener{

            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            viewModel.login(email, password)

        }

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))

}