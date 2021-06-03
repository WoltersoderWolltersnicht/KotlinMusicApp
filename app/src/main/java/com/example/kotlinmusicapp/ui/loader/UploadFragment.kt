package com.example.kotlinmusicapp.ui.loader

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.data.network.apis.UploadApi
import com.example.kotlinmusicapp.data.repository.UploadRepository
import com.example.kotlinmusicapp.databinding.FragmentUploadBinding
import com.example.kotlinmusicapp.ui.*
import com.example.kotlinmusicapp.ui.base.BaseFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class UploadFragment : BaseFragment<UploadViewModel, FragmentUploadBinding, UploadRepository>() {

    private var selectedImageUri: Uri? = null
    private var selectedAudioUri: Uri? = null
    private val READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!checkPermissionForReadExtertalStorage()) {
            try {
                requestPermissionForReadExtertalStorage()
            }catch(e: Exception) {
            }
        }

        binding.audioBtn.setOnClickListener {

            openAudioChooser()
        }

        binding.imageBtn.setOnClickListener{
            openImageChooser()
        }

        binding.buttonUpload.setOnClickListener {
            upload()
        }

        viewModel.updateResponse.observe(viewLifecycleOwner,  {
            //On Change
            when (it) {
                //On Success
                is Resource.Success -> {
                    Log.e("Login","Success")
                }
                //On Fail
                is Resource.Failure -> handleApiError(it)
            }
        })

    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, 101)
        }
    }

    private fun openAudioChooser() {
        Intent(Intent.ACTION_PICK).also {
            val intentUpload = Intent()
            intentUpload.type = "audio/*"
            intentUpload.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intentUpload, 102)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    selectedImageUri = data?.data
                    //image_view.setImageURI(selectedImageUri)
                }
                102 -> {
                    selectedAudioUri = data?.data
                    //image_view.setImageURI(selectedImageUri)
                }
            }
        }
    }

    //Returns Actual VM Class
    override fun getViewModel() = UploadViewModel::class.java

    //Returns Actual FragmentBinding
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) : FragmentUploadBinding = FragmentUploadBinding.inflate(inflater, container, false)

    //Returns Actual Fragment Repository
    override fun getFragmentRepository() = UploadRepository(remoteDataSource.buildApi(UploadApi::class.java))

    private fun upload(){
        if (selectedImageUri == null) {
            requireView().snackbar("Select an Image First")
            return
        }

        val parcelImgDescriptor = activity?.contentResolver?.openFileDescriptor(selectedImageUri!!, "r", null)?: return
        val imgStream = FileInputStream(parcelImgDescriptor.fileDescriptor)
        val img = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(selectedImageUri!!))
        imgStream.copyTo(FileOutputStream(img))
        val bodyImg = UploadRequestBody(img, "image")

        val parcelAudioDescriptor = activity?.contentResolver?.openFileDescriptor(selectedAudioUri!!, "r", null)?: return
        val audioStream = FileInputStream(parcelAudioDescriptor.fileDescriptor)
        val audio = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(selectedAudioUri!!))
        audioStream.copyTo(FileOutputStream(audio))
        val bodyAudio = UploadRequestBody(audio, "audio")


        binding.progressBarImg.progress = 0
        binding.progressBarAudio.progress = 0

        viewModel.uploadImage(
            MultipartBody.Part.createFormData(
                "image",
                img.name,
                bodyImg
            ),
            MultipartBody.Part.createFormData(
                "song",
                audio.name,
                bodyAudio
            ),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "json")
        )
    }

    fun checkPermissionForReadExtertalStorage(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result = requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    @Throws(Exception::class)
    fun requestPermissionForReadExtertalStorage() {
        try {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION_REQUEST_CODE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

}