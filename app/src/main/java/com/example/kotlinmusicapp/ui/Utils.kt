package com.example.kotlinmusicapp.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.kotlinmusicapp.R
import com.example.kotlinmusicapp.data.network.Resource
import com.example.kotlinmusicapp.ui.auth.fragments.LoginFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.json.Json.Default.context

class Utils{

    companion object {
        @JvmStatic
        val baseUrl : String = "http://spotify.rottinghex.com/"
        var userId : String = ""
    }

}

fun<A : Activity> Activity.changeFragment(activity : Class<A>,action:NavDirections){

    Navigation.findNavController(this, R.id.fragmentMain).navigate(action)

}

fun View.visible(isVisible: Boolean){

    visibility = if (isVisible) View.VISIBLE else View.GONE

}

fun  View.enable(enabled: Boolean){
    isEnabled = enabled
    alpha = if(enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null){
    val snackbar = Snackbar.make(this,message,Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (()->Unit)? =null
){
    when{
        failure.isNetworkError -> requireView().snackbar("Please check your internet connection", retry)
        failure.errorCode == 500 ->
            requireView().snackbar("Unavailable Server")
        failure.errorCode == 400 ->
            if(this is LoginFragment){
                requireView().snackbar("You have entered incorrect email or password")
            }else{
                //TODO: Logout
            }

        failure.errorCode ==462 ->
            requireView().snackbar("Email in use")

        else -> {
            val errorMessage = failure.errorBody?.string().toString()
            requireView().snackbar(errorMessage +" "+ failure.errorCode)
        }
    }
}

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}
