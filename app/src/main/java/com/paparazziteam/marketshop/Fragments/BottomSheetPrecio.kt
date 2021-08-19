package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paparazziteam.marketshop.Activities.ProductDetailsActivity
import com.paparazziteam.marketshop.databinding.BottomSheetPrecioBinding

class BottomSheetPrecio : BottomSheetDialogFragment() {

    var _binding: BottomSheetPrecioBinding ? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var newcode = arguments?.getString("code")
        Log.i("CODE RECEIVER","CODE: $newcode")

    }



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
        binding.editTextPrecio.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL

        Log.e("INPUTTYPE","INPUTTYPE: ${binding.editTextPrecio.inputType}")

        setOnclickListeners()


        return view
    }

    private fun setOnclickListeners() {

        binding.btnSave.setOnClickListener(View.OnClickListener {
            updateName()
        })

        binding.btnCancel.setOnClickListener(View.OnClickListener {
            dismiss()
        })
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