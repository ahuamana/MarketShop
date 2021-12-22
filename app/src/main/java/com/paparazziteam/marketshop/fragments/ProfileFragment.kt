package com.paparazziteam.marketshop.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.paparazziteam.marketshop.providers.AuthProvider
import com.paparazziteam.marketshop.providers.UserProvider
import com.paparazziteam.marketshop.utils.StaticUtil
import com.paparazziteam.marketshop.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    var mAuth = AuthProvider()
    var mUser = UserProvider()

    var extraEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        extraEmail= activity?.intent?.getStringExtra("username").toString()

        getUserInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        var view = binding.root

        //All your code here

        setOnClickListener()


        return view
    }

    private fun getUserInfo() {

        if (!extraEmail.equals("") && extraEmail!= null) {
            var mDialog: ProgressDialog? = null
            mDialog = ProgressDialog(context)
            mDialog!!.setTitle("Espere un momento")
            mDialog!!.setMessage("Cargando Información")
            mDialog.show()

            mUser.getUserInfo(extraEmail).get().addOnSuccessListener {
                if (it.exists()) {
                    var name = it.data!!.get("nombre")
                    var ape = it.data!!.get("apellidos")

                    name = StaticUtil.replaceFirstCharInSequenceToUppercase(name.toString())
                    ape = StaticUtil.replaceFirstCharInSequenceToUppercase(ape.toString())


                    binding.email.setText(extraEmail)
                    binding.emailMain.setText(extraEmail)
                    binding.fullname.setText("${name} ${ape}")

                    mDialog.dismiss()

                } else {
                    Log.e("DATA", "Username data no existe")
                    mDialog.dismiss()
                }
            }.addOnFailureListener {
                Log.e("DATA", "Error al cargar datos")
                mDialog.dismiss()
            }
        }

    }


    private fun setOnClickListener() {

        binding.linearLayoutSalir.setOnClickListener {

            if(mAuth.mAuth.currentUser != null)
            {
                mAuth.mAuth.signOut()
                Toast.makeText(context,"Session Cerrada, Exitosamente",Toast.LENGTH_SHORT).show()

            }
        }


    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {  }
            }

        @JvmStatic
        fun newInstance(email:String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString("username",email)
                }
            }
    }
}