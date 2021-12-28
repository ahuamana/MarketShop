package com.paparazziteam.marketshop.viewModels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.base.Strings.isNullOrEmpty
import com.paparazziteam.marketshop.activities.LoginEmailActivity
import com.paparazziteam.marketshop.activities.MainActivity
import com.paparazziteam.marketshop.providers.AuthProvider
import com.paparazziteam.marketshop.providers.UserProvider
import com.paparazziteam.marketshop.databinding.ActivityLoginEmailBinding
import com.paparazziteam.marketshop.root.Global
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginEmailActivityViewModel() : ViewModel() {

    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private var _pass = MutableLiveData<String>()
    var pass:LiveData<String> = _pass

    init {
        mAuth= AuthProvider()
    }

    fun checkIfuserExist(user: String, pass: String) {

        Global.disableUserInteraction()

            if (!isNullOrEmpty(pass) && !isNullOrEmpty(user)) {

                mUserProvider.getUserInfo(user).get().addOnSuccessListener {
                    if (it.exists()) {

                        var us = it.data!!.get("username")
                        var pa = it.data!!.get("password")

                        if (us!!.equals(user) && pa!!.equals(pass)) {

                            //Login
                            loginEmail(user,pass)

                        } else {
                            Global.enableUSerInteraction()
                            Toast.makeText(Global.getAppContext(),"Correo o Contraseña Incorrecto!", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Global.enableUSerInteraction()
                        Toast.makeText(Global.getAppContext(),"Usuario No Registrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                    Global.enableUSerInteraction()
                    Toast.makeText(Global.getAppContext(),"Usuario y/o Contraseña vacios", Toast.LENGTH_SHORT).show()


            }

    }

    fun loginEmail(user: String, pass: String) {

        mAuth.login(user, pass).addOnCompleteListener { task->
            if (task.isSuccessful)
            {
                Toast.makeText(Global.getAppContext(),"Bienvenido!", Toast.LENGTH_SHORT).show()
                var intent = Intent(Global.getAppContext(), MainActivity::class.java).apply {
                    putExtra("username",user)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                Global.getAppContext().startActivity(intent)

                Global.enableUSerInteraction()
            }
            else {
                Log.w("TAG", "signInWithEmail:failure", task.exception)
                Global.enableUSerInteraction()
            }
        }


    }



    companion object{

        var mUserProvider = UserProvider()
        lateinit var mAuth:AuthProvider

    }

}