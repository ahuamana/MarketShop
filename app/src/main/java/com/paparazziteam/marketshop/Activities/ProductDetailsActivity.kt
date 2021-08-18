package com.paparazziteam.marketshop.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paparazziteam.marketshop.Fragments.BottomSheetName
import com.paparazziteam.marketshop.Fragments.BottomSheetPrecio
import com.paparazziteam.marketshop.Models.Product
import com.paparazziteam.marketshop.Providers.ProductProvider
import com.paparazziteam.marketshop.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    var mBottomSheetName = BottomSheetName()
    var mBottomSheetPrecio = BottomSheetPrecio()


    var mProductProvider = ProductProvider()

    var mProduct = Product()

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
            mBottomSheetName.show(supportFragmentManager, mBottomSheetName.tag)

        })

        binding.imageViewEditPrecio.setOnClickListener(View.OnClickListener {
            mBottomSheetPrecio = BottomSheetPrecio.newInstance(binding.textViewPrecio.text.toString())
            mBottomSheetPrecio.show(supportFragmentManager, mBottomSheetName.tag)

        })

        //Registrar data on firestore
        binding.btnRegistrarProductDetails.setOnClickListener(View.OnClickListener {

           createData()

        })


    }

    private fun createData() {

        var document = mProductProvider.mCollection.document().id

        mProduct.id = document
        mProduct.name = binding.textViewName.text.toString()


        mProduct.precioUnitario = binding.textViewPrecio.text.toString().toDouble()


        mProductProvider.createProduct(mProduct).addOnCompleteListener(OnCompleteListener { task->

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

        mProduct.barcode = intent.getStringExtra("CODE_RESULT").toString()
        Log.e("TAG","CODE: ${mProduct.barcode}")

        getDataFirestore()

        binding.textViewBarcode.setText(mProduct.barcode)


    }

    private fun getDataFirestore() {



        if (mProduct.barcode != null) {

            mProductProvider.getBarcodeInfo(mProduct.barcode).get().addOnSuccessListener(
                OnSuccessListener {documents ->

                    if(documents != null)
                    {

                        for (document in documents) {


                            //var data = document.data.get("precioUnitario").toString()
                            binding.textViewName.setText(document.data.get("name").toString())
                            binding.textViewPrecio.setText(document.data.get("precioUnitario").toString())

                            Log.e("TAG","documentSnapshot: ${document.data.get("precioUnitario")}")
                            //Log.e("TAG","documentSnapshot: ${document.data.get("name")}")
                            //Log.d("TAG", "${document.id} => ${document.data}")
                            break;
                        }



                    }else
                    {
                        Log.e("TAG","documentSnapshot: null")
                    }

                })

        }
    }
}