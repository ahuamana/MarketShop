package com.paparazziteam.marketshop.Activities

import RealPathUtil
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.paparazziteam.marketshop.Fragments.BottomSheetName
import com.paparazziteam.marketshop.Fragments.BottomSheetPrecio
import com.paparazziteam.marketshop.Models.Product
import com.paparazziteam.marketshop.Providers.ProductProvider
import com.paparazziteam.marketshop.databinding.ActivityProductDetailsBinding
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import java.io.File


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

        binding.fabSelectImage.setOnClickListener(View.OnClickListener {

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

        binding.fabSelectImage.setOnClickListener(View.OnClickListener {
            showCameraWhatsapp()
        })

    }



    private fun showCameraWhatsapp() {

        val options = Options().apply{
            ratio = Ratio.RATIO_AUTO                                    //Image/video capture ratio
            count = 1                                                   //Number of images to restrict selection count
            spanCount = 4                                               //Number for columns in grid
            path = "Pix/Camera"                                         //Custom Path For media Storage
            isFrontFacing = false                                       //Front Facing camera on start
            videoDurationLimitInSeconds = 10                            //Duration for video recording
            mode = Mode.Picture                                             //Option to select only pictures or videos or both
            flash = Flash.Auto                                          //Option to select flash type
            preSelectedUrls = ArrayList<Uri>()                          //Pre selected Image Urls
        }

        Log.e("TAG","CAMERA: ABIERTA")



        addPixToActivity(android.R.id.content ,options)
        {
            when (it.status)
            {
                PixEventCallback.Status.SUCCESS -> {
                    it.data

                    val intent: Intent = Intent(baseContext, ProductDetailsActivity::class.java).apply{
                        putExtra("CODE_RESULT",mProduct.barcode)
                        putExtra("CAMERA_RESULT",it.data.toString())
                    }

                    startActivity(intent)

                    Log.e("TAG","CAMERA RESULT: SUCCESS")
                    Log.e("TAG","CAMERA RESULT: ${it.data.toString()}")


                }//use results as it.data

                PixEventCallback.Status.BACK_PRESSED -> {

                    Log.e("TAG","CAMERA RESULT: FAIL")

                }// back pressed called
            }
        }



    }


    private fun createData() {

        var document = mProductProvider.mCollection.document().id

        mProduct.id = document
        mProduct.name = binding.textViewName.text.toString()


        mProduct.precioUnitario = binding.textViewPrecio.text.toString().toDouble()

        if(!binding.textViewName.text.toString().equals("Ingresa nombre de producto"))
        {
            if(!binding.textViewPrecio.text.toString().equals("0.0"))
            {
                if(!mProduct.photo.equals("null"))
                {
                    createProduct()
                }
                else
                {
                    Toast.makeText(applicationContext,"Debes guardar una ",Toast.LENGTH_SHORT).show()
                }

            }else
            {
                Toast.makeText(applicationContext,"El precio no puede ser 0.0",Toast.LENGTH_SHORT).show()
            }

        }else
        {
            Toast.makeText(applicationContext,"Debe asignar un nombre de producto ",Toast.LENGTH_SHORT).show()
        }


    }

    private fun createProduct() {

        mProductProvider.createProduct(mProduct).addOnCompleteListener(OnCompleteListener { task->

            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext,"Creado en la base de datos correctamente!",Toast.LENGTH_SHORT).show()
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

        mProduct.photo = intent.getStringExtra("CAMERA_RESULT").toString()
        Log.e("TAG","PHOTO: ${mProduct.photo}")


        getDataFirestore()

        binding.textViewBarcode.setText(mProduct.barcode)

        if(mProduct.photo!= null)
        {
            if(!mProduct.photo.equals("null"))
            {
                var tempUri = Uri.parse(mProduct.photo.subSequence(1,mProduct.photo.length-1).toString())

                //var uri = "content://media/external/file/7252".toUri()

                var path = RealPathUtil.getRealPath(this,tempUri)

                Log.e("TAG","PHOTO PATH: ${path}")
                Log.e("TAG","PHOTO URI: ${tempUri}")
                binding.circleImageProduct.setImageURI(null)

                val imgFile = File(path)
                binding.circleImageProduct.borderColor = 0
                binding.circleImageProduct.borderWidth = 0
                binding.circleImageProduct.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))


            }

        }


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