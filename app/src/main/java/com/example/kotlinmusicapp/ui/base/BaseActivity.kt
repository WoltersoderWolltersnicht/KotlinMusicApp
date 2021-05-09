package com.example.kotlinmusicapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.kotlinmusicapp.data.network.RemoteDataSource
import com.example.kotlinmusicapp.data.repository.BaseRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.internal.platform.android.ConscryptSocketAdapter.Companion.factory
import java.util.prefs.Preferences

abstract class BaseActivity<VM: ViewModel, B: ViewBinding, R: BaseRepository> : AppCompatActivity(){

    lateinit var  binding: B
    lateinit var  viewModel: VM
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        binding = getViewBinding()
        setContentView(binding.root)
    }
/*
    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }
*/

    //Gets
    abstract fun getViewModel() : Class<VM>

    abstract fun getViewBinding(): B

    abstract  fun getFragmentRepository() : R

}