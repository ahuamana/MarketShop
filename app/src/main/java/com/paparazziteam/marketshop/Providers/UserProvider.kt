package com.paparazziteam.marketshop.Providers

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserProvider {

    var mCollection:CollectionReference

    init {
        mCollection = Firebase.firestore.collection("Users")
    }

    fun getUserInfo(user:String) : DocumentReference
    {
        return mCollection.document(user)
    }

}