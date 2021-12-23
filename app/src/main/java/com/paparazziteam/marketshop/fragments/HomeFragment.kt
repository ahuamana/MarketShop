package com.paparazziteam.marketshop.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.mancj.materialsearchbar.MaterialSearchBar.OnSearchActionListener
import com.paparazziteam.marketshop.adapters.ProductAdapter
import com.paparazziteam.marketshop.models.Product
import com.paparazziteam.marketshop.providers.ProductProvider
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.databinding.FragmentHomeBinding
import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import com.paparazziteam.marketshop.providers.UserProvider


class HomeFragment : Fragment() {

    var mUser = UserProvider()

    var _binding:FragmentHomeBinding ? = null
    private val binding get() = _binding!!

    var mLinearLayoutManager: LinearLayoutManager ? = null
    var mProductProvider = ProductProvider()

    private lateinit var productList:ArrayList<Product>

    private lateinit var mListener: ListenerRegistration

    var extraEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        extraEmail= activity?.intent?.getStringExtra("username").toString()


    }

    private fun getUserInfo() {

        Log.e("DATA","Email recibido: ${extraEmail}")



        if(!extraEmail.equals("None"))
        {
            val mDialog: ProgressDialog?
            mDialog= ProgressDialog(context)
            mDialog!!.setTitle("Espere un momento")
            mDialog!!.setMessage("Cargando Información")
            mDialog.show()

            mUser.getUserInfo(extraEmail).get().addOnSuccessListener {
                if(it.exists())
                {
                    var name= it.data!!.get("nombre")
                    binding.username.setText(name.toString())
                    mDialog.dismiss()

                }else
                {
                    Log.e("DATA","Username data no existe")
                    mDialog.dismiss()
                }
            }.addOnFailureListener{
                Log.e("DATA","Error al cargar datos")
                mDialog.dismiss()
            }
        }else
        {
            //If user not log in
            binding.username.setText("Usuario!")
        }



    }

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
        getUserInfo()


        return view
    }

    private fun getProducts() {

        productList = arrayListOf<Product>()

        mListener = mProductProvider.getProductListByName("").addSnapshotListener { querySnapshot, error ->

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
                binding.recyclerViewProducts.adapter = ProductAdapter(productList, requireContext())
            }


        }


    }

    override fun onDestroy() {
        super.onDestroy()

        if(mListener != null)
        {
            mListener.remove()
            binding.recyclerViewProducts.adapter = null
        }
    }

    private fun setOnclickListeners() {

        binding.imgQrcode.setOnClickListener {
            android.util.Log.d("CLICKED","QRCODE")
            replaceFragment(GrabFragment.newInstance())
        }



        //implementar metodos para buscar


        binding.searchProduct.setOnSearchActionListener(object : OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {

            }
            override fun onSearchConfirmed(text: CharSequence) {

                android.util.Log.e("TAG","MENSAJE: ${text.toString().uppercase()}")
                //var new= StaticUtil.firstCharToLowercase(text.toString().uppercase())
                //android.util.Log.e("TAG","MENSAJE: $new")

            }
            override fun onButtonClicked(buttonCode: Int) {

            }
        })

        //Set Text Change
        binding.searchProduct.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                android.util.Log.d("LOG_TAG", " text changed " + binding.searchProduct.getText())
                updateSearchData(binding.searchProduct.getText().lowercase())
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

    }

    private fun updateSearchData(text:String) {

        mProductProvider.getProductListByName(text).addSnapshotListener{ querySnapshot,error ->

            if(querySnapshot!!.isEmpty)
            {
                android.util.Log.e("TAG","QUERY VACIO")

                //Search Using barcode
                updateSearchDataWithBarcode(text)

            }else
            {
                productList.clear() // limpiamos los campos

                for (productsnaptshot in  querySnapshot.documents)
                {
                    val product = productsnaptshot.toObject<Product>()

                    productList.add(product!!)
                }

                //fill Recycler view
                binding.recyclerViewProducts.adapter = ProductAdapter(productList, requireContext())
            }

        }

    }

    private fun updateSearchDataWithBarcode(text: String) {

        mProductProvider.getProductListByBarcode(text).addSnapshotListener{ querySnapshot, error ->

            if(querySnapshot!!.isEmpty)
            {
                android.util.Log.e("TAG","QUERY BARCODE VACIO")

            }else
            {
                productList.clear() // limpiamos los campos

                for (productsnaptshot in  querySnapshot.documents)
                {
                    val product = productsnaptshot.toObject<Product>()

                    productList.add(product!!)
                }

                //fill Recycler view
                binding.recyclerViewProducts.adapter = ProductAdapter(productList, requireContext())
            }

            if(querySnapshot.size() > 0)
            {
                android.util.Log.e("TAG","QUERY SNAPTSHOT CANTIDAD: ${querySnapshot.size()}")
            }

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

        @JvmStatic
        fun newInstance(email:String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString("username",email)
                }
            }
    }



}