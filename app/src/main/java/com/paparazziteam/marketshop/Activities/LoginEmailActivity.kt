package com.paparazziteam.marketshop.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.ActivityLoginEmailBinding

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        setOnClickListener()




    }

    private fun setOnClickListener() {

        binding.circularImageviewBack.setOnClickListener {
            finish()
        }

        binding.btnLogin.setOnClickListener {

            checkIfuserExist()

        }

    }

    private fun checkIfuserExist() {

        var user = binding.editextEmail.text.toString()
        var pass = binding.edittextPassword.text.toString()

        if(user != null)
        {
            if(!user.equals(""))
            {

            }
        }

    }
}