package com.paparazziteam.marketshop.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.paparazziteam.marketshop.Fragments.GrabFragment
import com.paparazziteam.marketshop.Fragments.HomeFragment
import com.paparazziteam.marketshop.Fragments.ProfileFragment
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.ViewModels.MainActivityViewModel
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

        viewModel = MainActivityViewModel(binding, this@MainActivity)

    }




    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }


}