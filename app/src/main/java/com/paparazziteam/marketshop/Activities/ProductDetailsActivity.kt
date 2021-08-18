package com.paparazziteam.marketshop.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        getDataFromIntent()
        setOnClickListener()



    }

    private fun setOnClickListener() {

        binding.backImage.setOnClickListener(View.OnClickListener {
            finish()
        })


    }

    private fun getDataFromIntent() {

        var code = intent.getStringExtra("CODE_RESULT")
        binding.textViewBarcode.setText(code)


    }
}