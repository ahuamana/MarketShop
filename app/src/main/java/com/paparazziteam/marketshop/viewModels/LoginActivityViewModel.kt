package com.paparazziteam.marketshop.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.common.base.Strings.isNullOrEmpty
import com.paparazziteam.marketshop.providers.AuthProvider

class LoginActivityViewModel :ViewModel() {

    private val _title = MutableLiveData<String>()
    val title:LiveData<String> = _title

    init {
        startVariables()
    }

    fun startVariables()
    {
        mAuth = AuthProvider()
        _title.value = "Market Shop"
    }

    fun checkUserLoginAlready() : String
    {
        var currentUser = mAuth.mAuth.currentUser?.email

        if(!isNullOrEmpty(currentUser))
        {
            Log.e("viewModel_login", "Iniciando Session!")
            Log.e("viewModel_login", "email: ${currentUser}")

        }else
        {
            currentUser = "None"
            Log.e("viewModel_login", ""+currentUser)
        }

        return currentUser.toString()

    }
    companion object{
        private lateinit var mAuth:AuthProvider
    }

}