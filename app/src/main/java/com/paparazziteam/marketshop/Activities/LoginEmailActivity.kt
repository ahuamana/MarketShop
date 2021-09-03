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




    }
}