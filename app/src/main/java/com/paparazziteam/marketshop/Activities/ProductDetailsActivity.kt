package com.paparazziteam.marketshop.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.paparazziteam.marketshop.Fragments.BottomSheetName
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.ActivityProductDetailsBinding
import com.paparazziteam.marketshop.databinding.BottomSheetNameBinding

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    val mBottomSheetName:BottomSheetName = BottomSheetName.newInstance()

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

        binding.imageViewEditNombre.setOnClickListener(View.OnClickListener {

            Log.i("CLICKED"," ON EDIT NAME")
            mBottomSheetName.show(supportFragmentManager,mBottomSheetName.tag)

        })

        binding.imageViewEditPrecio.setOnClickListener(View.OnClickListener {

        })




    }

    private fun getDataFromIntent() {

        var code = intent.getStringExtra("CODE_RESULT")
        binding.textViewBarcode.setText(code)


    }
}