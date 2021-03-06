package com.paparazziteam.marketshop.providers


import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paparazziteam.marketshop.models.Product


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

    fun getProductListByName(newText: String):Query
    {
        return mCollection.orderBy("name").startAt(newText.lowercase()).limit(25).endAt(newText.lowercase()+'\uf8ff')
    }

    fun getProductListByBarcode(newText: String):Query
    {
        return mCollection.orderBy("barcode").startAt(newText.lowercase()).limit(25).endAt(newText.lowercase()+'\uf8ff')
    }

    /*
    fun getBarcodeInfo(barcode: String) : Query
    {
        return mCollection
            .whereEqualTo("barcode",barcode)
    }
     */





}