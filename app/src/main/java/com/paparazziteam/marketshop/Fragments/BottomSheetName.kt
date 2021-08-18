package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paparazziteam.marketshop.R

public class BottomSheetName : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.bottom_sheet_name,container,false)
    }

    companion object{

        fun newInstance(): BottomSheetName = BottomSheetName().apply {
            /*
                    arguments = Bundle().apply {
                        putInt(ARG_ITEM_COUNT, itemCount)
                    }
           */
        }
    }

}