package com.example.kotlinmusicapp.ui.home


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.kotlinmusicapp.R
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.apis.AuthApi
import com.example.kotlinmusicapp.data.repository.AuthRepository
import com.example.kotlinmusicapp.databinding.FragmentRegisterBinding
import com.example.kotlinmusicapp.ui.auth.AuthViewModel
import com.example.kotlinmusicapp.ui.base.BaseFragment
import com.example.kotlinmusicapp.ui.handleApiError
import com.example.kotlinmusicapp.ui.visible
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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