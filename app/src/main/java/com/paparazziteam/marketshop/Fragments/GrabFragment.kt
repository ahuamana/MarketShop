package com.paparazziteam.marketshop.Fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.paparazziteam.marketshop.databinding.FragmentGrabBinding


class GrabFragment : Fragment() {

    private var _binding:FragmentGrabBinding ? = null
    private val binding get() = _binding!!

    val CAMERA_PERMISION_CODE = 100
    private lateinit var codeScanner: CodeScanner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //android.util.Log.e("ON CREATE","FRAGMENT")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrabBinding.inflate(inflater,container,false)
        var view = binding.root

        android.util.Log.e("ON CREATEVIEW","FRAGMENT")

        codeScanner = CodeScanner(requireActivity(), _binding!!.scannerView)

        //clicksListener()



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
                Toast.makeText(requireActivity(), it.text, Toast.LENGTH_LONG).show()
                android.util.Log.e("Scan result","QR CODE: ${it.text}")
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


        _binding?.scannerView?.setOnClickListener {
            codeScanner.startPreview()
        }

    }


    override fun onStop() {
        super.onStop()
        android.util.Log.e("ON STOP","FRAGMENT")
        if(codeScanner!= null)
        {
            android.util.Log.e("ON PAUSE","RUN")
            codeScanner.releaseResources()
        }
    }

    private fun checkPermision(permission: String, requesCode: Int)
    {
        if(ContextCompat.checkSelfPermission(requireContext(),permission)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission),requesCode)

        }else
        {
            android.util.Log.e("CAMERA PERMISION","Permision Granted Already")
            getDataCodeScanner()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CAMERA_PERMISION_CODE)
        {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                android.util.Log.e("CAMERA PERMISION","Permision Granted Already")
                getDataCodeScanner()
            }else
            {
                android.util.Log.e("CAMERA PERMISION","Denied")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GrabFragment().apply {
                arguments = Bundle().apply {  }

            }
    }



}