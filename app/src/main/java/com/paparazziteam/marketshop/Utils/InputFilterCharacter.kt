package com.paparazziteam.marketshop.Utils

import android.text.InputFilter
import android.text.Spanned
import java.net.URLDecoder

class InputFilterCharacter : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {


        if(isCharacter(source.toString()))
        {
            return source.toString();
        }
        return "";

    }

    private fun isCharacter(s: String): Boolean {
        /*return s.matches( Regex(".*[a-zA-Z]+.*"))*/  //letras
        var data = "%5C"

        //^[1-9]\d*(\.\d+)?$
         data= URLDecoder.decode(data,"UTF-8")

        android.util.Log.e("TAG","REGEX: ${data}")
        android.util.Log.e("TAG","REGEX MACHES: "+ "^[1-9]${data}d*(${data}.${data}d+)?\$")


        return s.matches( "^[1-9]${data}d*(${data}.${data}d+)?$".toRegex())
    }


}




