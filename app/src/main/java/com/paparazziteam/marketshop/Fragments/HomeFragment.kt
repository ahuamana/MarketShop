package com.paparazziteam.marketshop.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.toObject
import com.paparazziteam.marketshop.Adapters.ProductAdapter
import com.paparazziteam.marketshop.Models.Product
import com.paparazziteam.marketshop.Providers.ProductProvider
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    var _binding:FragmentHomeBinding ? = null
    private val binding get() = _binding!!

    var mLinearLayoutManager: LinearLayoutManager ? = null
    var mProductProvider = ProductProvider()

    private lateinit var productList:ArrayList<Product>

   
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        var view = binding.root

        //Code Here

        mLinearLayoutManager = LinearLayoutManager(context)
        mLinearLayoutManager!!.stackFromEnd = true
        binding.recyclerViewProducts.layoutManager = mLinearLayoutManager

        setOnclickListeners()

        getProducts()



        return view
    }

    private fun getProducts() {

        productList = arrayListOf<Product>()

        mProductProvider.getProductListByName().addSnapshotListener { querySnapshot, error ->

            if(querySnapshot!!.isEmpty)
            {
                android.util.Log.e("TAG","QUERY VACIO")
            }else
            {
                for (productsnaptshot in  querySnapshot.documents)
                {
                    val product = productsnaptshot.toObject<Product>()
                    productList.add(product!!)
                }

                //fill Recycler view
                binding.recyclerViewProducts.adapter = ProductAdapter(productList)
            }


        }


    }

    private fun setOnclickListeners() {

        binding.imgQrcode.setOnClickListener {
            android.util.Log.d("CLICKED","QRCODE")
            replaceFragment(GrabFragment.newInstance())
        }

    }


    fun replaceFragment(fragment: Fragment)
    {
        val fragmentTransition = parentFragmentManager.beginTransaction()

        fragmentTransition
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(Fragment::class.java.simpleName)
            .commit()

        android.util.Log.d("FRAGMENT","REEMPLAZADO")
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {  }
            }
    }



}