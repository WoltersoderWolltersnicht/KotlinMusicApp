package com.example.kotlinmusicapp.ui.auth.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.data.repository.RegisterRepository
import com.example.kotlinmusicapp.data.responses.RegisterResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class RegisterViewModel (
    private val repository: RegisterRepository
        ): ViewModel(){

    //LiveDatas for Register Responses
    private val _registerResponse : MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<RegisterResponse>> get() = _registerResponse

    //Register Method
    fun register(//Needs name, email, password
        name: String,
        email: String,
        password: String
        //Cooroutine
    ) = viewModelScope.launch {
        //Saves login response to MutableLiveData var
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.register(name,email, password)
    }

    fun validName(name : TextInputLayout) : Boolean{
        if (name.editText?.text.toString().trim().isNullOrBlank()){
            name.error = "Name cant be empty"
            return false
        }
        name.error = null
        return true
    }

    fun validEmail(email : TextInputLayout) : Boolean{
        if (email.editText?.text.toString().trim().isNullOrBlank()){
            email.error = "Email cant be empty"
            return false
        }
        email.error = null
        return true
    }

    fun validPasswords(pass1 : TextInputLayout, pass2 : TextInputLayout) : Boolean{

        if (pass1.editText?.text.toString().trim().isNullOrBlank()){
            pass1.error = "Password cant be empty"
            return false
        }

        if (pass1.editText?.text.toString().trim() == pass2.editText?.text.toString().trim()){
            pass2.error = "Passwords are not the same"
            return false
        }

        pass1.error = null
        pass2.error = null
        return true

    }

}

