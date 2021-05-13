package com.example.kotlinmusicapp.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.kotlinmusicapp.data.network.apis.AuthApi
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.apis.RegisterApi
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.data.repository.RegisterRepository
import com.example.kotlinmusicapp.databinding.FragmentRegisterBinding
import com.example.kotlinmusicapp.ui.auth.AuthFragment
import com.example.kotlinmusicapp.ui.auth.AuthViewModel
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.enable
import com.example.kotlinmusicapp.ui.handleApiError
import com.example.kotlinmusicapp.ui.snackbar
import com.example.kotlinmusicapp.ui.visible
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding,RegisterRepository>() {

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

                        lifecycleScope.launch {

                            val action = RegisterFragmentDirections.actionRegistrationFragmentToLoginFragment(binding.editTextTextEmailAddress.editText?.text.toString())

                            Navigation.findNavController(binding.root).navigate(action)

                            val f : NavHostFragment = parentFragment as NavHostFragment
                            val parent : AuthFragment = f.parentFragment as AuthFragment
                            parent.changeEnabled();

                            requireView().snackbar("User Created Correctly")

                        }

                }
                //On Fail
                is Resource.Failure -> handleApiError(it)
            }
        })

        //Login Click Listener
        binding.btnRegister.setOnClickListener{

            //Gets Data
            val name = binding.editTextTextName
            val email = binding.editTextTextEmailAddress
            val password = binding.editTextTextPassword
            val password2 = binding.editTextTextPassword2

            val vName = viewModel.validName(name)
            val vEmail = viewModel.validEmail(email)
            val vPass = viewModel.validPasswords(password,password2)

            if(vName && vEmail && vPass) {
                //Launch Login Process
                viewModel.register(
                    name.editText?.text.toString().trim(),
                    email.editText?.text.toString().trim(),
                    password.editText?.text.toString().trim()
                )
            }
        }

    }

    //Returns Actual VM Class
    override fun getViewModel() = RegisterViewModel::class.java

    //Returns Actual Fragment Binding
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) : FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater,container,false)

    //Returns Actual Fragment Repository
    override fun getFragmentRepository() = RegisterRepository(remoteDataSource.buildApi(RegisterApi::class.java))



}