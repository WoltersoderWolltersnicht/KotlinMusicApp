package com.example.kotlinmusicapp.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.kotlinmusicapp.MainActivity
import com.example.kotlinmusicapp.databinding.FragmentLoginBinding
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.apis.LoginApi
import com.example.kotlinmusicapp.data.repository.LoginRepository
import com.example.kotlinmusicapp.ui.auth.AuthFragmentDirections
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.changeFragment
import com.example.kotlinmusicapp.ui.handleApiError
import com.example.kotlinmusicapp.ui.visible

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Progressbar Not visible
        binding.progressbar.visible(false)

        //Observer Observing LoginResponse
        viewModel.loginResponse.observe(viewLifecycleOwner,  {
            //On Change
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                //On Success
                is Resource.Success -> {
                    val action = AuthFragmentDirections.actionAuthFragmentToMysongFragment()
                    requireActivity().changeFragment(MainActivity::class.java,action)
                    Log.e("Login","Success")
                }
                //On Fail
                is Resource.Failure -> handleApiError(it)
                else -> {

                }
            }
        })

        //TODO: Only enable Login button when bouth edit text are not Empty

        //Login Click Listener
        binding.btnLogin.setOnClickListener{

            //Gets Data
            val email = binding.editTextTextEmailAddress.editText?.text.toString().trim()
            val password = binding.editTextTextPassword.editText?.text.toString().trim()

            //Launch Login Process
            viewModel.login(email, password)

        }

    }

    //Returns Actual VM Class
    override fun getViewModel() = LoginViewModel::class.java

    //Returns Actual FragmentBinding
    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) : FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    //Returns Actual Fragment Repository
    override fun getFragmentRepository() = LoginRepository(remoteDataSource.buildApi(LoginApi::class.java))

}