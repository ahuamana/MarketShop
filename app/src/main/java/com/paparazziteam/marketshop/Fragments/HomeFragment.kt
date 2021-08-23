package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    var _binding:FragmentHomeBinding ? = null
    private val binding get() = _binding!!
   
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        var view = binding.root

        //Code Here

        setOnclickListeners()



        return view
    }

    private fun setOnclickListeners() {

        binding.imgQrcode.setOnClickListener {
            android.util.Log.d("CLICKED","QRCODE")
            replaceFragment(GrabFragment.newInstance())
        }

    }


    fun replaceFragment(fragment: Fragment)
    {
        val fragmentTransition = parentFragmentManager.beginTransaction()

        fragmentTransition
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName)
            .commit()

        android.util.Log.d("FRAGMENT","REEMPLAZADO")
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {  }
            }
    }



}