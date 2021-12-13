package com.paparazziteam.marketshop.ViewModels

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.paparazziteam.marketshop.Fragments.GrabFragment
import com.paparazziteam.marketshop.Fragments.HomeFragment
import com.paparazziteam.marketshop.Fragments.ProfileFragment
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.ActivityMainBinding

class MainActivityViewModel(private val binding: ActivityMainBinding,private val context: Context) : ViewModel() {

    init {

        getExtras()
        setBotomNavigation()
    }

    fun getExtras()
    {
        var userReceiver =  (context as Activity).intent.extras!!["username"].toString()

        if(userReceiver.equals(""))
        {
            email = "Usuario!"
        }else
        {
            email = userReceiver
        }

    }

    fun setBotomNavigation()
    {
        //first time added home fragment
        addFragment(HomeFragment.newInstance(email))

        with(binding.bottomNavigation) {
            this?.add(MeowBottomNavigation.Model(1, R.drawable.ic_home))
            this?.add(MeowBottomNavigation.Model(2, R.drawable.ic_camera))
            this?.add(MeowBottomNavigation.Model(3, R.drawable.ic_person))
        }

        binding.bottomNavigation.setOnClickMenuListener {

            when(it.id)
            {
                1 -> {

                    android.util.Log.d("CLICKED","HOME")
                    replaceFragment(HomeFragment.newInstance(email))
                }

                2 -> {
                    android.util.Log.d("CLICKED","GRAB CAMERA")
                    replaceFragment(GrabFragment.newInstance())
                }
                3 -> {
                    android.util.Log.d("CLICKED","PROFILE")
                    replaceFragment(ProfileFragment.newInstance(email))
                }


            }



        }

    }



    //This code replace current fragment with other
    fun replaceFragment(fragment: Fragment)
    {
        val fragmentTransition = (context as AppCompatActivity).supportFragmentManager.beginTransaction()

        fragmentTransition
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName)
            .commit()

        android.util.Log.d("FRAGMENT","REEMPLAZADO")
    }

    fun addFragment(fragment: Fragment)
    {
        val fragmentTransition =  (context as AppCompatActivity).supportFragmentManager.beginTransaction()

        fragmentTransition
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName)
            .commit()

        android.util.Log.d("FRAGMENT","ADD FRAGMENT")
    }


    companion object{
        var email = ""
    }
}