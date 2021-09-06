package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    var _binding:FragmentProfileBinding ? = null
    private val binding get() = _binding!!

    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        var view = binding.root

        //All your code here



        return view
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