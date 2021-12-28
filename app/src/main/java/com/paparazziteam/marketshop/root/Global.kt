package com.paparazziteam.marketshop.root

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.WindowManager
import com.squareup.okhttp.internal.Internal.instance

lateinit var context: Context

class Global: Application() {

    companion object{

        fun getAppContext(): Context {

            return context
        }

        fun setAppContext(contexto: Context)
        {
            context = contexto
        }

        fun disableUserInteraction()
        {
            (context as Activity).window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

        fun enableUSerInteraction()
        {
            (context as Activity).window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

    }



}