package com.paparazziteam.marketshop.viewModels

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.paparazziteam.marketshop.databinding.ActivityMainBinding

class MainActivityViewModel(private val binding: ActivityMainBinding,private val context: Context) : ViewModel() {

    init {

        getExtras()

    }

    fun getExtras()
    {
        var userReceiver =  (context as Activity).intent.extras!!["username"].toString()

        if(userReceiver.equals(""))
        {
            email = "Usuario!"
        }else
        {
            email = userReceiver
        }

    }




    companion object{
        var email = ""
    }
}