package com.paparazziteam.marketshop.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import com.paparazziteam.marketshop.Providers.AuthProvider
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.ViewModels.LoginActivityViewModel
import com.paparazziteam.marketshop.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginActivityViewModel
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = LoginActivityViewModel(this@LoginActivity, binding)

    }



    override fun onStart() {
        super.onStart()

        viewModel.checkUserLoginAlready()

    }
}