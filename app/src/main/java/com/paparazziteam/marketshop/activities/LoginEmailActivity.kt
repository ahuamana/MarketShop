package com.paparazziteam.marketshop.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.paparazziteam.marketshop.viewModels.LoginEmailActivityViewModel
import com.paparazziteam.marketshop.databinding.ActivityLoginEmailBinding
import com.paparazziteam.marketshop.root.Global
import kotlinx.coroutines.launch

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding


    var viewModel: LoginEmailActivityViewModel = LoginEmailActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set Context
        Global.setAppContext(this)

        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //all code here



        binding.circularImageviewBack.setOnClickListener {
            finish()
        }

        binding.btnLogin.setOnClickListener {

            binding.cortina.visibility = View.VISIBLE
            login()
            binding.cortina.visibility = View.GONE
        }

    }

    private fun login() {

            var user=binding.editextEmail.text.toString()
            var pass=binding.edittextPassword.text.toString()
            viewModel.checkIfuserExist(user,pass)



    }


}