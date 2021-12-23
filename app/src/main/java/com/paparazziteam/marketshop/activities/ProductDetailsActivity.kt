package com.paparazziteam.marketshop.activities

import RealPathUtil
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.paparazziteam.marketshop.fragments.BottomSheetName
import com.paparazziteam.marketshop.fragments.BottomSheetPrecio
import com.paparazziteam.marketshop.models.Product
import com.paparazziteam.marketshop.providers.ImageProvider
import com.paparazziteam.marketshop.providers.ProductProvider
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.utils.StaticUtil
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
    var mImageProvider = ImageProvider()

    var mProduct = Product()

    var isCameraOpen = false

    private var mDialog:ProgressDialog ? = null

    var dataExiste: String ? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getDataFromIntent()

        mDialog= ProgressDialog(this@ProductDetailsActivity)

        mDialog!!.setTitle("Espere un momento")
        mDialog!!.setMessage("Guardando Información")

        setOnClickListener()

    }

    private fun setOnClickListener() {

        //return to main activity
        binding.backImage.setOnClickListener(View.OnClickListener {

            val intent: Intent = Intent(baseContext, MainActivity::class.java).apply{
                putExtra("username","")

            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) //Eliminar actividades que quedaron atras
            startActivity(intent)

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

            if(dataExiste.equals("true"))
            {
                //actualizar
                updateData()
            }else
            {
                //actualizar campos
                createData()
            }


        })

        binding.fabSelectImage.setOnClickListener(View.OnClickListener {
            showCameraWhatsapp()
        })

    }

    private fun updateData() {



        if(!binding.textViewName.text.toString().equals("Ingresa nombre de producto"))
        {
            if(!binding.textViewPrecio.text.toString().equals("0.0"))
            {
                if(!mProduct.photo.equals("null"))
                {
                    if(mProduct.photo != null)
                    {
                        updateProduct()
                    }
                }
                else
                {
                    Toast.makeText(this@ProductDetailsActivity,"Debes añadir una foto ",Toast.LENGTH_SHORT).show()
                }

            }else
            {
                Toast.makeText(this@ProductDetailsActivity,"El precio no puede ser 0.0",Toast.LENGTH_SHORT).show()
            }

        }else
        {
            Toast.makeText(this@ProductDetailsActivity,"Debe asignar un nombre de producto ",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProduct() {

        mDialog!!.show()

        if(!mProduct.photo.contains("https"))
        {
            Log.e("TAG","La photo no contiene https")
            //show real path from URI
            var tempUri = Uri.parse(mProduct.photo.subSequence(1,mProduct.photo.length-1).toString())
            //var uri = "content://media/external/file/7252".toUri()
            var path = RealPathUtil.getRealPath(this,tempUri)

            val imgFile = File(path)

            mImageProvider.save(applicationContext,imgFile).addOnCompleteListener {

                if(it.isSuccessful)
                {

                    //Inicia otra tarea para descargar la URL que se subira a firestorage
                    mImageProvider.getDownloadUri().addOnSuccessListener {

                        mProduct.photo = it.toString()
                        updateProductOnFirebase()

                    }

                }else
                {
                    mDialog!!.dismiss()
                    Toast.makeText(this@ProductDetailsActivity, "No se pudo almacenar la imagen", Toast.LENGTH_SHORT).show()
                }
            }

                .addOnFailureListener{
                    mDialog!!.dismiss()
                    Log.e("ERROR","Error al subir una photo ->${it.message} ")
                }
        }else
        {
            updateProductOnFirebase()
        }


    }

    private fun updateProductOnFirebase() {

        mProductProvider.update(mProduct).addOnSuccessListener {

            mDialog!!.dismiss()
            Toast.makeText(this@ProductDetailsActivity, "Los datos se actualizaron correctamente", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener{
                mDialog!!.dismiss()
                Log.e("ERROR","Error al actualizar los datos ->${it.message} ")
            }
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

        isCameraOpen = !isCameraOpen


        addPixToActivity(android.R.id.content ,options)
        {
            when (it.status)
            {
                PixEventCallback.Status.SUCCESS -> {

                    mProduct.photo = it.data.toString()

                    Log.e("TAG","PRECIO ENVIADO: ${binding.textViewPrecio.text}")
                    Log.e("TAG","NOMBRE ENVIADO: ${binding.textViewName.text}")

                    val intent: Intent = Intent(baseContext, ProductDetailsActivity::class.java).apply{
                        putExtra("CODE_RESULT",mProduct.barcode)
                        putExtra("CAMERA_RESULT", mProduct.photo)
                        putExtra("NOMBRE",binding.textViewName.text.toString())
                        putExtra("PRECIO",binding.textViewPrecio.text)
                        putExtra("EXISTE",dataExiste)
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

        if(!binding.textViewName.text.toString().equals("Ingresa nombre de producto"))
        {
            if(!binding.textViewPrecio.text.toString().equals("0.0"))
            {
                if(!mProduct.photo.equals("null"))
                {
                    if(mProduct.photo != null)
                    {
                        createProduct()
                    }
                }
                else
                {
                    Toast.makeText(this@ProductDetailsActivity,"Debes añadir una foto ",Toast.LENGTH_SHORT).show()
                }

            }else
            {
                Toast.makeText(this@ProductDetailsActivity,"El precio no puede ser 0.0",Toast.LENGTH_SHORT).show()
            }

        }else
        {
            Toast.makeText(this@ProductDetailsActivity,"Debe asignar un nombre de producto ",Toast.LENGTH_SHORT).show()
        }


    }

    private fun createProduct() {

        mDialog!!.show()

        //mProduct.name = binding.textViewName.text.toString()
        //mProduct.precioUnitario = binding.textViewPrecio.text.toString().toDouble()

        //show real path from URI
        var tempUri = Uri.parse(mProduct.photo.subSequence(1,mProduct.photo.length-1).toString())
        //var uri = "content://media/external/file/7252".toUri()
        var path = RealPathUtil.getRealPath(this,tempUri)

        val imgFile = File(path)

        mImageProvider.save(applicationContext,imgFile).addOnCompleteListener {

            if(it.isSuccessful)
            {

                //Inicia otra tarea para descargar la URL que se subira a firestorage
                mImageProvider.getDownloadUri().addOnSuccessListener {

                    mProduct.photo = it.toString()
                    saveOnfirebase()

                }

            }else
            {
                mDialog!!.dismiss()
                Toast.makeText(this@ProductDetailsActivity, "No se pudo almacenar la imagen", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{

            mDialog!!.dismiss()
            Toast.makeText(this@ProductDetailsActivity,"Revisa tu conexion a internet o contacta con un administrador",Toast.LENGTH_SHORT).show()
        }




    }

    fun saveOnfirebase()
    {
        mProductProvider.createProduct(mProduct).addOnCompleteListener{ task->

            if(task.isSuccessful)
            {
                mDialog!!.dismiss()
                Toast.makeText(applicationContext,"Creado en la base de datos correctamente!",Toast.LENGTH_SHORT).show()
                Log.i("TAG","id creado: true")

                finish() // finish activity

            }else
            {
                Log.i("TAG","id creado: false")
                mDialog!!.dismiss()
            }

        }.addOnFailureListener {

            mDialog!!.dismiss()
            Toast.makeText(this@ProductDetailsActivity,"Revisa tu conexion a internet o contacta con un administrador",Toast.LENGTH_SHORT).show()
        }
    }

    //Update on XML data
    fun setNameNew(nameNew: String)
    {
        binding.textViewName.setText(nameNew)
        mProduct.name = nameNew.lowercase()

        Log.i("TAG","NOMBRE ASIGNADO: ${mProduct.name}")
    }

    fun setPrecioNew(precioNew: String)
    {
        binding.textViewPrecio.setText(precioNew)
        mProduct.precioUnitario =  precioNew.toDouble()

        Log.i("TAG","PRECIO ASIGNADO: ${mProduct.precioUnitario}")
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getDataFromIntent() {

        mProduct.barcode = intent.getStringExtra("CODE_RESULT").toString()
        Log.e("TAG","CODE RECIBIDO: ${mProduct.barcode}")

        mProduct.photo = intent.getStringExtra("CAMERA_RESULT").toString()

        Log.e("TAG","PHOTO RECIBIDO: ${mProduct.photo}")


        mProduct.name = intent.getStringExtra("NOMBRE").toString()
        Log.e("TAG","NOMBRE RECIBIDO: ${mProduct.name}")

        if(!mProduct.name.equals("null"))
        {

            binding.textViewName.text = StaticUtil.replaceFirstCharInSequenceToUppercase(mProduct.name)
        }

        var precio = intent.getStringExtra("PRECIO").toString()
        Log.e("TAG","PRECIO RECIBIDO: ${precio}")

        if(!precio.equals("null"))
        {
            mProduct.precioUnitario = precio.toDouble()
            //Log.e("TAG","PRECIO RECIBIDO: ${precio}")
            binding.textViewPrecio.text = mProduct.precioUnitario.toString()
        }

        dataExiste = intent.getStringExtra("EXISTE").toString()
        Log.e("TAG","DATA EXISTE: ${dataExiste}")

        if(dataExiste.equals("true"))
        {
            binding.btnRegistrarProductDetails.setText("ACTUALIZAR")
            binding.btnRegistrarProductDetails.isEnabled = true
        }else
        {
            binding.btnRegistrarProductDetails.setText("REGISTRAR")
            binding.btnRegistrarProductDetails.isEnabled = true
        }



        binding.textViewBarcode.setText(mProduct.barcode)

        if(mProduct.photo!= null)
        {
            Log.e("TAG","PHOTO PATH: PHOTO PATH ES DIFERENTE A NULL")

           if(!mProduct.photo.equals("null"))
           {
               Log.e("TAG","PHOTO PATH: PHOTO PATH NO CONTIENE NULL")

               if(!mProduct.photo.equals(""))
               {
                   //Remove border and color when image it's set
                   binding.circleImageProduct.borderColor = 0
                   binding.circleImageProduct.borderWidth = 0

                  if(mProduct.photo.contains("https"))
                  {
                      Log.e("TAG","PHOTO PATH: containg HTTPS")

                      Glide.with(this@ProductDetailsActivity)
                          .load(mProduct.photo)
                          .placeholder(R.drawable.ic_launcher_background)
                          .into(binding.circleImageProduct)

                  }
                  else
                  {
                      Log.e("TAG","PHOTO PATH: PHOTO PATH ES DIFERENTE A VACIO")

                      //show real path from URI
                      var tempUri = Uri.parse(mProduct.photo.subSequence(1,mProduct.photo.length-1).toString())
                      //var uri = "content://media/external/file/7252".toUri()
                      var path = RealPathUtil.getRealPath(this,tempUri)

                      val imgFile = File(path)
                      //mProduct.photo = path!!

                      Log.e("TAG","PHOTO PRODUCT PATH: ${mProduct.photo}")
                      Log.e("TAG","PHOTO URI: ${tempUri}")
                      binding.circleImageProduct.setImageURI(null)
                      binding.circleImageProduct.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
                  }



               }
               else
               {
                   binding.circleImageProduct.setImageDrawable(resources.getDrawable(R.drawable.ic_image))
               }

           }else
           {
               binding.circleImageProduct.setImageDrawable(resources.getDrawable(R.drawable.ic_image))
           }


        }else
        {
            binding.circleImageProduct.setImageDrawable(resources.getDrawable(R.drawable.ic_image))
        }


    }

    override fun onBackPressed() {

        Log.e("TAG","ONBACKPRESSED")

        if(isCameraOpen)
        {
            isCameraOpen= !isCameraOpen
            val intent: Intent = Intent(baseContext, ProductDetailsActivity::class.java).apply{
                putExtra("CODE_RESULT",mProduct.barcode)
                putExtra("CAMERA_RESULT",mProduct.photo)
                putExtra("NOMBRE",binding.textViewName.text.toString())
                putExtra("PRECIO",binding.textViewPrecio.text)
                putExtra("EXISTE",dataExiste)

            }
            startActivity(intent)

        }else
        {
            val intent: Intent = Intent(baseContext, MainActivity::class.java).apply{
                putExtra("username","")
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) //Eliminar actividades que quedaron atras
            startActivity(intent)
        }
    }





}