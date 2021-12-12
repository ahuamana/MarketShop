package com.paparazziteam.marketshop.ViewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.paparazziteam.marketshop.Activities.MainActivity
import com.paparazziteam.marketshop.Providers.AuthProvider

class LoginActivityViewModel(private var mAuthProvider: AuthProvider,private val context: Context) :ViewModel() {


    fun checkUserLoginAlready()
    {
        val currentUser =  mAuthProvider.mAuth.currentUser

        if(currentUser != null)
        {
            Log.e("TAG", "Iniciando Session!")
            Log.e("TAG", "Email: ${currentUser.email}")

            var intent = Intent(context, MainActivity::class.java).apply {
                putExtra("username",currentUser.email)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }

}