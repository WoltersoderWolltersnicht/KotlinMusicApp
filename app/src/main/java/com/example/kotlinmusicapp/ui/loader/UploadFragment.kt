package com.example.kotlinmusicapp.ui.loader

import com.example.kotlinmusicapp.R
import okhttp3.RequestBody.Companion.toRequestBody
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
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import com.example.kotlinmusicapp.ui.Utils

class UploadFragment : BaseFragment<UploadViewModel, FragmentUploadBinding, UploadRepository>() {

    private var selectedImageUri: Uri? = null
    private var selectedAudioUri: Uri? = null
    private val READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)

        loadUI()

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

        binding.btnUpload.setOnClickListener {

            upload()
        }

        viewModel.updateResponse.observe(viewLifecycleOwner,  {
            //On Change
            binding.progressbar.visible(true)
            binding.btnUpload.visible(false)
            when (it) {

                //On Success
                is Resource.Success -> {
                    val songId = it.value.songId

                    if(songId!=null && selectedImageUri != null){
                        uploadImage(songId)
                    }else{
                        requireView().snackbar("Song added correctly")
                    }

                }
                //On Fail
                is Resource.Failure -> {
                    Log.e("Upload","Error")
                    handleApiError(it)
                }
            }
            binding.progressbar.visible(false)
            binding.btnUpload.visible(true)
        })

        viewModel.updateResponse.observe(viewLifecycleOwner,  {
            //On Change
            binding.progressbar.visible(it is Resource.Loading)
            binding.btnUpload.visible(it !is Resource.Loading)

            when (it) {

                //On Success
                is Resource.Success -> {
                    requireView().snackbar("Song added correctly")
                }
                //On Fail
                is Resource.Failure -> {
                    Log.e("Upload", "Error")
                    requireView().snackbar("Error adding Img")
                    handleApiError(it)
                }
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
                    binding.imageName.text = selectedImageUri.toString()
                    binding.img.setImageURI(selectedImageUri)
                }
                102 -> {
                    selectedAudioUri = data?.data
                    binding.audioName.text = selectedAudioUri.toString()
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
        val name = binding.txtSongName
        val auth = binding.txtSongArtist
        val gender = binding.txtGenerName

        var vName:Boolean = viewModel.validSongName(name)
        var vAuth:Boolean = viewModel.validAuthor(auth)
        var vGender:Boolean = viewModel.validGender(gender)

        if (selectedAudioUri == null) {
            requireView().snackbar("Select an Audio File")
            return
        }

        if(!vName&&!vAuth&&!vGender) return

        var audioPart:MultipartBody.Part?= getFileMultipat("song",selectedAudioUri!!,"audio")
        if (audioPart==null) return

        viewModel.upload(
            audioPart,
            name.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            auth.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            gender.editText?.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            "1".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            Utils.userId.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            )

    }

    private fun uploadImage(songId:String){

        var imgPart:MultipartBody.Part?= getFileMultipat("image",selectedImageUri!!,"image")
        if (imgPart==null) return

        viewModel.uploadImage(imgPart,songId.toRequestBody("multipart/form-data".toMediaTypeOrNull()))

    }

    private fun getFileMultipat(contentName:String, selectedFileUrl:Uri, contentType:String): MultipartBody.Part? {
        val parcelFile = activity?.contentResolver?.openFileDescriptor(selectedFileUrl, "r", null)?: return null
        val fileStream = FileInputStream(parcelFile.fileDescriptor)
        val file = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(selectedFileUrl))
        fileStream.copyTo(FileOutputStream(file))
        val body = UploadRequestBody(file, contentType)

        return MultipartBody.Part.createFormData(contentName,file.name,body)
    }

    private fun checkPermissionForReadExtertalStorage(): Boolean {
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

    private fun loadUI(){

        binding.txtGenerName.editText?.setText("")
        binding.txtSongArtist.editText?.setText("")
        binding.txtSongName.editText?.setText("")

        binding.audioName.text = ""
        binding.imageName.text = ""

        binding.img.setImageResource(R.drawable.background)

        selectedImageUri = null
        selectedAudioUri = null

    }

}