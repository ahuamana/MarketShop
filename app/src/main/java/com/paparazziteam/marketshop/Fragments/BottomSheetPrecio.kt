package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paparazziteam.marketshop.Activities.ProductDetailsActivity
import com.paparazziteam.marketshop.databinding.BottomSheetPrecioBinding

import android.text.InputFilter




class BottomSheetPrecio : BottomSheetDialogFragment() {

    var _binding: BottomSheetPrecioBinding ? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetPrecioBinding.inflate(inflater,container,false)
        var view = binding.root


        var precio = arguments?.getString("precio")
        Log.e("PRECIO RECEIVER","PRECIO: $precio")


        //binding.editTextPrecio.setText(precio)






        setOnclickListeners()


        return view
    }

    private fun setOnclickListeners() {

        binding.btnSave.setOnClickListener{
            updateName()
        }

        binding.btnCancel.setOnClickListener{
            dismiss()
        }
    }

    private fun updateName() {

        var newPrecio = binding.firstNumber.value.toString()+ "." + binding.afterPeriod.value.toString() + binding.secondAfterPeriod.value.toString()

        if(!newPrecio.equals(""))
        {
            (activity as ProductDetailsActivity?)!!.setPrecioNew(newPrecio)
            dismiss()
        }

    }



    companion object{

        fun newInstance(precio: String): BottomSheetPrecio = BottomSheetPrecio().apply {

                    arguments = Bundle().apply {
                        putString("precio", precio)
                    }

        }

    }

}