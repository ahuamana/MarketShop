package com.paparazziteam.marketshop.providers

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthProvider {

    var mAuth: FirebaseAuth

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    fun login (email:String, password:String) : Task<AuthResult>
    {
        return mAuth.signInWithEmailAndPassword(email,password)
    }


}

