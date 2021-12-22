package com.paparazziteam.marketshop.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paparazziteam.marketshop.providers.AuthProvider

class LoginActivityViewModel :ViewModel() {

    private val _title = MutableLiveData<String>()
    val title:LiveData<String> = _title

    init {
        startVariables()
        //clickListeners()
    }

    fun startVariables()
    {
        mAuth = AuthProvider()
        _title.value = "Market Shop"

    }

    fun checkUserLoginAlready() : String
    {
        var currentUser = mAuth.mAuth.currentUser?.email

        if(currentUser != null && !currentUser.equals(""))
        {
            Log.e("viewModel_login", "Iniciando Session!")
            Log.e("viewModel_login", "email: ${currentUser}")

        }else
        {
            currentUser = "None"
            Log.e("viewModel_login", ""+currentUser)
        }

        return currentUser

    }

    fun clickListeners()
    {
        /*
            binding.loginAnonymous.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("username", "")
                }
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }

            binding.loginEmail.setOnClickListener {
                val intent = Intent(context, LoginEmailActivity::class.java)
                context.startActivity(intent)
            }

         */

    }

    companion object{
        private lateinit var mAuth:AuthProvider
    }

}