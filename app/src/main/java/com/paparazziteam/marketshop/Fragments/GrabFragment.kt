package com.paparazziteam.marketshop.Fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.paparazziteam.marketshop.Activities.ProductDetailsActivity
import com.paparazziteam.marketshop.Providers.ProductProvider
import com.paparazziteam.marketshop.databinding.FragmentGrabBinding


class GrabFragment : Fragment() {

    private var _binding:FragmentGrabBinding ? = null
    private val binding get() = _binding!!

    val CAMERA_PERMISION_CODE = 100
    private lateinit var codeScanner: CodeScanner

    private var mProductProvider:ProductProvider = ProductProvider()

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    { isGranted:Boolean ->
        if(isGranted)
        {
            android.util.Log.i("Permision","Granted")
            getDataCodeScanner()
        } else
        {
            android.util.Log.i("Permision","Denied")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrabBinding.inflate(inflater,container,false)
        var view = binding.root

        android.util.Log.e("ON CREATEVIEW","FRAGMENT")

        codeScanner = CodeScanner(requireActivity(), binding.scannerView)

        clicksListener()



        checkPermision(Manifest.permission.CAMERA, CAMERA_PERMISION_CODE)



        return view
    }

    private fun clicksListener() {

        _binding!!.scannerView.setOnClickListener {
            codeScanner.startPreview()
            android.util.Log.e("CLICKED","START SCANNER")
        }
    }


    fun getDataCodeScanner()
    {

        android.util.Log.e("INICIA","GETDATASCANNER")

        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not

        //Decode code
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {

                android.util.Log.e("Scan result","QR CODE: ${it.text}")

                showNextActivity(it.text.toString())

                //Toast.makeText(requireActivity(), it.text, Toast.LENGTH_LONG).show()

            }
        }

        // or ErrorCallback.SUPPRESS
        codeScanner.errorCallback = ErrorCallback {
            requireActivity().runOnUiThread(Runnable {
                //on main thread
                android.util.Log.e("ERROR","Camera initialization error: ${it.message}")
            })

        }

        codeScanner.startPreview()


        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    private fun showNextActivity(barcode: String) {



            if (barcode != null)
            {
                Log.e("TAG","BARCODE: $barcode es diferente de nulo")

                mProductProvider.getProductInfo(barcode).get().addOnSuccessListener {document->

                    if(!document.exists())
                    {
                        //If document exist on db
                        Log.e("TAG","documentSnapshot: no existe")
                        val intent: Intent = Intent(requireContext(), ProductDetailsActivity::class.java).apply{
                            putExtra("CODE_RESULT",barcode)
                            putExtra("EXISTE","false")
                        }

                        startActivity(intent)

                    }else
                    {
                        var barcode=document.data!!.get("barcode").toString()
                        var name=document.data!!.get("name").toString()
                        var precio=document.data!!.get("precioUnitario").toString()
                        var photo=document.data!!.get("photo").toString()

                        Log.e("TAG","documentSnapshot: ${document.data!!.get("precioUnitario")}")
                        //Log.e("TAG","documentSnapshot: ${document.data.get("name")}")
                        //Log.d("TAG", "${document.id} => ${document.data}")


                        val intent: Intent = Intent(requireContext(), ProductDetailsActivity::class.java).apply{
                            putExtra("CODE_RESULT",barcode)
                            putExtra("NOMBRE",name)
                            putExtra("PRECIO",precio)
                            putExtra("CAMERA_RESULT",photo)
                            putExtra("EXISTE","true")

                        }

                        startActivity(intent)



                    }

                }


        }





    }


    override fun onStop() {
        super.onStop()
        Log.e("ON STOP","FRAGMENT")
        if(codeScanner!= null)
        {
           Log.e("ON PAUSE","RUN")
            codeScanner.releaseResources()
        }
    }

    private fun checkPermision(permission: String, requesCode: Int)
    {
        if(ContextCompat.checkSelfPermission(requireContext(),permission)==PackageManager.PERMISSION_DENIED)
        {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)

        }else
        {
            Log.e("CAMERA PERMISION","Permision Granted Already")
            getDataCodeScanner()
        }

    }

    override fun onResume() {
        super.onResume()

        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }





    companion object {
        @JvmStatic
        fun newInstance() =
            GrabFragment().apply {
                arguments = Bundle().apply {  }

            }
    }



}