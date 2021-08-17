package com.paparazziteam.marketshop.Fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    val REQUEST_ID_MULTIPLE_PERMISSIONS = 7


    private lateinit var codeScanner: CodeScanner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrabBinding.inflate(inflater,container,false)
        val view = binding.root

        checkAndroidVersion(view)






        return view
    }

    fun getDataCodeScanner(view: View)
    {
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, _binding!!.scannerView)


        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }

        _binding!!.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }



        // or ErrorCallback.SUPPRESS
        codeScanner.errorCallback = ErrorCallback {
            activity?.runOnUiThread(Runnable {
                //on main thread
                Toast.makeText(view.context, "Camera initialization error: ${it.message}",Toast.LENGTH_LONG).show()
                android.util.Log.e("ERROR","Camera initialization error: ${it.message}")
            })

        }

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun checkAndroidVersion( view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             var verdad= checkAndRequestPermissions()
                if(verdad)
                {
                    getDataCodeScanner(view)
                }

        } else {
            // code for lollipop and pre-lollipop devices
        }
    }


    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        )
        val write = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val read =
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (write != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            GrabFragment().apply {
                arguments = Bundle().apply {  }

            }
    }



}