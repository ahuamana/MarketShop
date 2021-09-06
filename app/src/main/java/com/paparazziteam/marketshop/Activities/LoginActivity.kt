package com.paparazziteam.marketshop.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        getClickListener()

    }

    private fun getClickListener() {

        binding.loginAnonymous.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.loginEmail.setOnClickListener {

            val intent = Intent(this@LoginActivity, LoginEmailActivity::class.java)
            startActivity(intent)
        }

    }
}