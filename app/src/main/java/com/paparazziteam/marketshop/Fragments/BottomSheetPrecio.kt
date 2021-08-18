package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paparazziteam.marketshop.Activities.ProductDetailsActivity
import com.paparazziteam.marketshop.databinding.BottomSheetNameBinding
import com.paparazziteam.marketshop.databinding.BottomSheetPrecioBinding

public class BottomSheetPrecio : BottomSheetDialogFragment() {

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
        Log.i("PRECIO RECEIVER","PRECIO: $precio")


        binding.editTextUsername.setText(precio)


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

        var newPrecio = binding.editTextUsername.text.toString().trimEnd()

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