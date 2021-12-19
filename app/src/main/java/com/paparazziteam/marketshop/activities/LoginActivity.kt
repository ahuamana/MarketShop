package com.paparazziteam.marketshop.activities

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

        setContentView(binding.root)
    }



    override fun onStart() {
        super.onStart()

        _viewModel.checkUserLoginAlready()

    }
}

