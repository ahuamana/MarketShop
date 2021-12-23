package com.paparazziteam.marketshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paparazziteam.marketshop.viewModels.LoginEmailActivityViewModel
import com.paparazziteam.marketshop.databinding.ActivityLoginEmailBinding

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding

    lateinit var viewModel: LoginEmailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = LoginEmailActivityViewModel(binding, this@LoginEmailActivity)


    }
}