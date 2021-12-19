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
        mAuth = AuthProvider()
        //clickListeners()
    }

    fun startVariables()
    {
        _title.value = "Market Shop"
    }

    fun checkUserLoginAlready()
    {
        val currentUser =  mAuth.mAuth.currentUser

        if(currentUser != null)
        {
            Log.e("TAG", "Iniciando Session!")
            Log.e("TAG", "Email: ${currentUser.email}")

            /*
            var intent = Intent(context, MainActivity::class.java).apply {
                putExtra("username",currentUser.email)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)*/
        }

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