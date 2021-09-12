package com.paparazziteam.marketshop.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.paparazziteam.marketshop.Providers.AuthProvider
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    var _binding:FragmentProfileBinding ? = null
    private val binding get() = _binding!!

    var mAuth = AuthProvider()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        var view = binding.root

        //All your code here

        setOnClickListener()

        getUserInfo()

        return view
    }

    private fun getUserInfo() {

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