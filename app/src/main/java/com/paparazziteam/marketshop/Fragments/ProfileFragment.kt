package com.paparazziteam.marketshop.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.paparazziteam.marketshop.Providers.AuthProvider
import com.paparazziteam.marketshop.Providers.UserProvider
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.Utils.StaticUtil
import com.paparazziteam.marketshop.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    var mAuth = AuthProvider()
    var mUser = UserProvider()

    var emailReceiver = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            emailReceiver = it.getString("username")!!
        }

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

        if (mAuth.mAuth.currentUser != null) {
            var mDialog: ProgressDialog? = null
            mDialog = ProgressDialog(context)
            mDialog!!.setTitle("Espere un momento")
            mDialog!!.setMessage("Cargando Información")
            mDialog.show()

            mUser.getUserInfo(emailReceiver).get().addOnSuccessListener {
                if (it.exists()) {
                    var name = it.data!!.get("nombre")
                    var ape = it.data!!.get("apellidos")

                    name = StaticUtil.replaceFirstCharInSequenceToUppercase(name.toString())
                    ape = StaticUtil.replaceFirstCharInSequenceToUppercase(ape.toString())


                    binding.email.setText(emailReceiver)
                    binding.emailMain.setText(emailReceiver)
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