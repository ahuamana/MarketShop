package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paparazziteam.marketshop.Activities.ProductDetailsActivity
import com.paparazziteam.marketshop.databinding.BottomSheetPrecioBinding
import com.paparazziteam.marketshop.Utils.InputFilterCharacter

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


        binding.editTextPrecio.setText(precio)

        binding.editTextPrecio.setFilters(arrayOf<InputFilter>(InputFilterCharacter()))




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

        var newPrecio = binding.editTextPrecio.text.toString().trimEnd()

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