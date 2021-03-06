package com.example.kotlinmusicapp.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kotlinmusicapp.MainActivity
import com.example.kotlinmusicapp.databinding.FragmentLoginBinding
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.apis.LoginApi
import com.example.kotlinmusicapp.data.repository.LoginRepository
import com.example.kotlinmusicapp.ui.*
import com.example.kotlinmusicapp.ui.auth.AuthFragmentDirections
import com.example.kotlinmusicapp.ui.base.BaseFragment

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {

    val args : LoginFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Progressbar Not visible
        binding.progressbar.visible(false)
        binding.btnLogin.visible(true)

        binding.editTextTextEmailAddress.editText?.setText(args.email.toString());

        //Observer Observing LoginResponse
        viewModel.loginResponse.observe(viewLifecycleOwner,  {
            //On Change
            binding.progressbar.visible(it is Resource.Loading)
            binding.btnLogin.visible(it !is Resource.Loading)
            when (it) {
                //On Success
                is Resource.Success -> {
                    Utils.userId = it.value.user.id.toString()
                    val action = AuthFragmentDirections.actionAuthFragmentToHomeFragment()
                    activity?.changeFragment(MainActivity::class.java,action)
                    Log.e("Login","Success")
                }
                //On Fail
                is Resource.Failure -> handleApiError(it)
            }
        })

        //TODO: Only enable Login button when bouth edit text are not Empty

        //Login Click Listener
        binding.btnLogin.setOnClickListener{

            //Gets Data
            val email = binding.editTextTextEmailAddress
            val password = binding.editTextTextPassword

            val vEmail = viewModel.validEmail(email)
            val vPass = viewModel.validPassword(password)
            //Launch Login Process
            if( vEmail && vPass){

                viewModel.login(email.editText?.text.toString().trim(), password.editText?.text.toString().trim())

            }

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