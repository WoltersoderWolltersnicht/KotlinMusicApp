package com.example.kotlinmusicapp.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.kotlinmusicapp.data.network.apis.AuthApi
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.databinding.FragmentRegisterBinding
import com.example.kotlinmusicapp.ui.auth.AuthViewModel
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.handleApiError
import com.example.kotlinmusicapp.ui.visible
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding,AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Progressbar Not visible
        binding.progressbar.visible(false)

        //Observer Observing LoginResponse
        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            //On Change
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                //On Success
                is Resource.Success -> {
                    if(it.value.message.equals("Succes")){
                        lifecycleScope.launch {
                            //Calls Utils startNewActivity to call next Activity
                            //requireActivity().startNewActivity(HomeActivity::class.java)

                            //Info
                            Log.e("Register","Success")
                        }
                    }else{

                        //Info
                        Log.e("Register",it.value.message)
                    }


                }
                //On Fail
                is Resource.Failure -> handleApiError(it)
            }
        })

        //TODO: Only enable Register button when bouth edit text are not Empty && Check input before send

        //Login Click Listener
        binding.btnRegister.setOnClickListener{

            //Gets Data
            val name = binding.editTextTextName.editText?.text.toString().trim()
            val email = binding.editTextTextEmailAddress.editText?.text.toString().trim()
            val password = binding.editTextTextPassword.editText?.text.toString().trim()
            val password2 = binding.editTextTextPassword2.editText?.text.toString().trim()

            //Launch Login Process
            viewModel.register(name, email, password)

        }

    }

    //Returns Actual VM Class
    override fun getViewModel() = AuthViewModel::class.java

    //Returns Actual Fragment Binding
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater,container,false)

    //Returns Actual Fragment Repository
    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))



}