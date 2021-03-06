package com.paparazziteam.marketshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.paparazziteam.marketshop.fragments.GrabFragment
import com.paparazziteam.marketshop.fragments.HomeFragment
import com.paparazziteam.marketshop.fragments.ProfileFragment
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.viewModels.MainActivityViewModel
import com.paparazziteam.marketshop.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //Your Code Here

        //Log.e("EXTRA", "${intent.getStringExtra("username")}")
        //intent.getStringExtra("username")

        viewModel = MainActivityViewModel()

        setBotomNavigation()
    }

    fun setBotomNavigation()
    {
        //first time added home fragment
        addFragment(HomeFragment.newInstance(MainActivityViewModel.email))

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
                    replaceFragment(HomeFragment.newInstance(MainActivityViewModel.email))
                }

                2 -> {
                    android.util.Log.d("CLICKED","GRAB CAMERA")
                    replaceFragment(GrabFragment.newInstance())
                }
                3 -> {
                    android.util.Log.d("CLICKED","PROFILE")
                    replaceFragment(ProfileFragment.newInstance(MainActivityViewModel.email))
                }


            }



        }

    }



    //This code replace current fragment with other
    fun replaceFragment(fragment: Fragment)
    {
        //val fragmentTransition = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        val fragmentTransition = supportFragmentManager.beginTransaction()

        fragmentTransition
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName)
            .commit()

        android.util.Log.d("FRAGMENT","REEMPLAZADO")
    }

    fun addFragment(fragment: Fragment)
    {
        val fragmentTransition =  (this as AppCompatActivity).supportFragmentManager.beginTransaction()

        fragmentTransition
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName)
            .commit()

        android.util.Log.d("FRAGMENT","ADD FRAGMENT")
    }




    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }


}