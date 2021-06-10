package com.example.kotlinmusicapp.ui.auth.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.repository.LoginRepository
import com.example.kotlinmusicapp.data.responses.LoginResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel (
    private val repository: LoginRepository
    ):ViewModel(){

    //LiveDatas for Login Responses
    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>> get() = _loginResponse

    //Login Method
    fun login(email: String,password: String) {

        viewModelScope.launch {
            //Saves login response to MutableLiveData var
            _loginResponse.value = Resource.Loading
            _loginResponse.value = repository.login(email, password)
        }
    }

    fun validEmail(email : TextInputLayout) : Boolean{

        val emailAdress = email.editText?.text.toString().trim()

        email.error = null

        if (email.editText?.text.toString().trim().isNullOrBlank()){
            email.error = "Email cant be empty"
            return false
        }
        if(emailAdress!="1") {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAdress).matches()) {
                email.error = "Invalid Email"
                return true
            }
        }
        email.error = null
        return true

    }

    fun validPassword(password : TextInputLayout) : Boolean{

        if (password.editText?.text.toString().trim().isNullOrBlank()){
            password.error = "Password cant be empty"
            return false
        }

        password.error = null
        return true

    }

}