package com.paparazziteam.marketshop.activities

import android.app.Activity
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

        //all code here
        viewModel = LoginEmailActivityViewModel(binding, this@LoginEmailActivity)

        binding.circularImageviewBack.setOnClickListener {
            finish()
        }

        binding.btnLogin.setOnClickListener {

            //checkIfuserExist()

        }



    }
}