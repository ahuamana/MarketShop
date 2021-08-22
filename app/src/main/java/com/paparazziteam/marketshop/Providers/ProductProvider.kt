package com.paparazziteam.marketshop.Providers


import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paparazziteam.marketshop.Models.Product


class ProductProvider {

    var mCollection: CollectionReference

    init {
        mCollection = Firebase.firestore.collection("Products")
    }


    fun createProduct(product:Product): Task<Void>
    {
        return mCollection.document(product.barcode).set(product)
    }


    fun getProductInfo(barcode:String) :DocumentReference
    {
        return mCollection.document(barcode)
    }

    fun update(product: Product):Task<Void>
    {
        //var map = HashMap<String>()

        //Actualizar username
        val map = mutableMapOf<String,Any?>()
        map.put("barcode",product.barcode)
        map.put("name",product.name)
        map.put("precioUnitario",product.precioUnitario)
        map.put("photo",product.photo)

        return mCollection.document(product.barcode).update(map)
    }

    /*
    fun getBarcodeInfo(barcode: String) : Query
    {
        return mCollection
            .whereEqualTo("barcode",barcode)
    }
     */





}