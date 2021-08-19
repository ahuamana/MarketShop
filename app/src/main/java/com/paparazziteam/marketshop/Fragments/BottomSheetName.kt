package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paparazziteam.marketshop.Activities.ProductDetailsActivity
import com.paparazziteam.marketshop.databinding.BottomSheetNameBinding

public class BottomSheetName : BottomSheetDialogFragment() {

    var _binding: BottomSheetNameBinding ? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //var newcode = arguments?.getString("name")
        //Log.i("NAME RECEIVER","name: $newcode")

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetNameBinding.inflate(inflater,container,false)
        var view = binding.root


        var name = arguments?.getString("name")
        Log.i("NAME RECEIVER","name: $name")
        binding.editTextName.setText(name)



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

        var newName = binding.editTextName.text.toString().trimEnd()

        if(!newName.equals(""))
        {
            (activity as ProductDetailsActivity?)!!.setNameNew(newName)
            dismiss()
        }

    }

    companion object{

        fun newInstance(name: String): BottomSheetName = BottomSheetName().apply {

                    arguments = Bundle().apply {
                        putString("name", name)
                    }

        }

    }

}