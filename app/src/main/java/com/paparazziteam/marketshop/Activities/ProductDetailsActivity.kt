package com.paparazziteam.marketshop.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paparazziteam.marketshop.Fragments.BottomSheetName
import com.paparazziteam.marketshop.Fragments.BottomSheetPrecio
import com.paparazziteam.marketshop.Models.Product
import com.paparazziteam.marketshop.Providers.ProductProvider
import com.paparazziteam.marketshop.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    var mBottomSheetName:BottomSheetName? = null
    var mBottomSheetPrecio: BottomSheetPrecio? = null


    var mProductProvider: ProductProvider ?= null

    var mProduct: Product ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getDataFromIntent()




        setOnClickListener()



    }

    private fun setOnClickListener() {

        //return to main activity
        binding.backImage.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.imageViewEditName.setOnClickListener(View.OnClickListener {

            mBottomSheetName = BottomSheetName.newInstance(binding.textViewName.text.toString())
            mBottomSheetName!!.show(supportFragmentManager, mBottomSheetName!!.tag)

        })

        binding.imageViewEditPrecio.setOnClickListener(View.OnClickListener {
            mBottomSheetPrecio = BottomSheetPrecio.newInstance(binding.textViewPrecio.text.toString())
            mBottomSheetPrecio!!.show(supportFragmentManager, mBottomSheetName!!.tag)

        })

        //Registrar data on firestore
        binding.btnRegistrarProductDetails.setOnClickListener(View.OnClickListener {

           createData()

        })


    }

    private fun createData() {

        var document = Firebase.firestore.collection("Products").document()

        mProduct!!.id = document.toString()

        mProductProvider!!.createProduct(mProduct!!).addOnCompleteListener(OnCompleteListener { task->

            if(task.isSuccessful)
            {
                Log.i("TAG","id creado: true")
            }else
            {
                Log.i("TAG","id creado: false")
            }


        })
    }

    fun setNameNew(nameNew: String)
    {
        binding.textViewName.setText(nameNew)
    }

    fun setPrecioNew(precioNew: String)
    {
        binding.textViewPrecio.setText(precioNew)
    }

    private fun getDataFromIntent() {

        mProduct!!.code = intent.getStringExtra("CODE_RESULT").toString()

        getDataFirestore()

        binding.textViewBarcode.setText(mProduct!!.code)


    }

    private fun getDataFirestore() {

        mProductProvider = ProductProvider()

        if (mProduct!!.code != null) {

            mProductProvider!!.getBarcodeInfo(mProduct!!.code).get().addOnCompleteListener(
                OnCompleteListener { querySnapshot ->

                    if(querySnapshot != null)
                    {

                    }else
                    {
                        Log.i("TAG","querysnapshot: null")
                    }


                })
        }
    }
}