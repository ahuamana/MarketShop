package com.paparazziteam.marketshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.paparazziteam.marketshop.viewModels.LoginActivityViewModel
import com.paparazziteam.marketshop.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val _viewModel:LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@LoginActivity
            viewmodel = _viewModel
        }




        binding.loginEmail.setOnClickListener {
            openLoginWithEmail()
        }
        binding.loginAnonymous.setOnClickListener {
            openAnonymousPrincipal()
        }


        setContentView(binding.root)
    }

    fun openLoginWithEmail()
    {
        var i = Intent(applicationContext,LoginEmailActivity::class.java)
        startActivity(i)
    }

    fun openAnonymousPrincipal()
    {
        var i = Intent(applicationContext,MainActivity::class.java).apply {
            putExtra("username", email)
        }
        startActivity(i)
    }


    override fun onResume() {
        super.onResume()
         email=_viewModel.checkUserLoginAlready()

        if(email!= null && !email.equals("None"))
        {
            openAnonymousPrincipal()
        }else
        {
            android.util.Log.e("login_activity","email:"+ email)
        }
    }

    companion object{

        var email: String = "None"
    }
}

