package com.paparazziteam.marketshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etebarian.meowbottomnavigation.MeowBottomNavigation


class MainActivity : AppCompatActivity() {

    var bottomNavigation: MeowBottomNavigation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        botomNavigation()



    }

    fun botomNavigation()
    {

        with(bottomNavigation) {
            this?.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            this?.add(MeowBottomNavigation.Model(2, R.drawable.ic_camera))
            this?.add(MeowBottomNavigation.Model(3, R.drawable.ic_person))
        }
    }

    fun linkXML()
    {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }
}