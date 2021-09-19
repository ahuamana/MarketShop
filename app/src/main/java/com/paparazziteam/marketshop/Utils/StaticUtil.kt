package com.paparazziteam.marketshop.Utils

class StaticUtil {


    companion object {


        var newInstance = this

        @JvmStatic
        fun firstCharToLowercase(text: String): String {

            var subString = text.replaceFirstChar {
                it.lowercase()
            }

            return subString
        }
        @JvmStatic
        fun replaceFirstCharInSequenceToLowecase(text: String): String
        {

            val words = text.split(' ')
            var subString = words.joinToString("_") { word ->
                word.replaceFirstChar {
                    it.lowercase()
                }

            }

            return subString


        }

        fun replaceFirstCharInSequenceToUppercase(text: String): String
        {

            val words = text.split(' ');
            var subString = words.joinToString(" ") { word ->
                word.replaceFirstChar {
                    it.uppercase()
                }

            }

            return subString


        }


    }

}