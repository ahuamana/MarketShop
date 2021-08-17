package com.paparazziteam.marketshop.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.paparazziteam.marketshop.Fragments.GrabFragment
import com.paparazziteam.marketshop.Fragments.HomeFragment
import com.paparazziteam.marketshop.Fragments.ProfileFragment
import com.paparazziteam.marketshop.R


class MainActivity : AppCompatActivity() {

    var bottomNavigation: MeowBottomNavigation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linkXML();



        setBotomNavigation()



    }

    fun setBotomNavigation()
    {
        addFragment(HomeFragment.newInstance())

        with(bottomNavigation) {
            this?.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            this?.add(MeowBottomNavigation.Model(2, R.drawable.ic_camera))
            this?.add(MeowBottomNavigation.Model(3, R.drawable.ic_person))
        }

        bottomNavigation!!.setOnClickMenuListener {

            when(it.id)
            {
                1 -> {
                    android.util.Log.d("CLICKED","HOME")
                    replaceFragment(HomeFragment.newInstance())
                }

                2 -> {
                    android.util.Log.d("CLICKED","GRAB CAMERA")
                    replaceFragment(GrabFragment.newInstance())
                }
                3 -> {
                    android.util.Log.d("CLICKED","PROFILE")
                    replaceFragment(ProfileFragment.newInstance())
                }


            }



        }

    }

    fun linkXML()
    {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    //This code replace current fragment with other
    fun replaceFragment(fragment: Fragment)
    {
        val fragmentTransition = supportFragmentManager.beginTransaction()


            fragmentTransition
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(Fragment::class.java.simpleName)
                .commit()

            android.util.Log.d("FRAGMENT","REEMPLAZADO")


    }

    fun addFragment(fragment: Fragment)
    {
        val fragmentTransition = supportFragmentManager.beginTransaction()


        fragmentTransition
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName)
            .commit()

        android.util.Log.d("FRAGMENT","ADD FRAGMENT")


    }
}