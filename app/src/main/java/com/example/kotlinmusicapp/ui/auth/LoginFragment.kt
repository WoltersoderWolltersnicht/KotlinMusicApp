package com.example.kotlinmusicapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.kotlinmusicapp.databinding.FragmentLoginBinding
import com.example.kotlinmusicapp.data.network.AuthApi
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.enable
import com.example.kotlinmusicapp.ui.handleApiError
import com.example.kotlinmusicapp.ui.home.HomeActivity
import com.example.kotlinmusicapp.ui.startNewActivity
import com.example.kotlinmusicapp.ui.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Progressbar and btnLogin Not visible
        binding.progressbar.visible(false)
        binding.btnLogin.enable(false)

        //Observer Observing LoginResponse
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            //On Change
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                //On Success
                is Resource.Success -> {
                    //Save AuthToken
                    lifecycleScope.launch {
                        //Calls Utils startNewActivity to call next Activity
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                    //Info
                    Log.e("Login","Success")
                }
                //On Fail
                is Resource.Failure -> handleApiError(it)
            }
        })

        /*
        Only enable Login button when bouth edit text are not Empty
        TODO: This has bugs: FIX
        */
        binding.editTextTextPassword.editText?.addTextChangedListener {
            val email = binding.editTextTextEmailAddress.editText?.text.toString().trim()
            binding.btnLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        //Click Listener
        binding.btnLogin.setOnClickListener{

            //Gets Data
            val email = binding.editTextTextEmailAddress.editText?.text.toString().trim()
            val password = binding.editTextTextPassword.editText?.text.toString().trim()

            //Launch Login Process
            viewModel.login(email, password)

        }

    }

    //Returns Actual VM Class
    override fun getViewModel() = AuthViewModel::class.java

    //Returns Actual FragmentBinding
    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    //Retuns Actual Fragment Repository
    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))

}