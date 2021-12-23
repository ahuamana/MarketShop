package com.paparazziteam.marketshop.viewModels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.paparazziteam.marketshop.activities.MainActivity
import com.paparazziteam.marketshop.providers.AuthProvider
import com.paparazziteam.marketshop.providers.UserProvider
import com.paparazziteam.marketshop.databinding.ActivityLoginEmailBinding

class LoginEmailActivityViewModel(private val binding:ActivityLoginEmailBinding,private val context: Context) : ViewModel() {

    init {
        mAuth= AuthProvider()
        setOnClickListener()
    }

    private fun setOnClickListener() {

        binding.circularImageviewBack.setOnClickListener {
            (context as Activity).finish()
        }

        binding.btnLogin.setOnClickListener {

            checkIfuserExist()

        }

    }

    private fun checkIfuserExist() {

        var user = binding.editextEmail.text.toString()
        var pass = binding.edittextPassword.text.toString()

        if(user != null)
        {
            if(!user.equals(""))
            {
                if(pass != null)
                {
                    if(!pass.equals(""))
                    {

                        mUserProvider.getUserInfo(user).get().addOnSuccessListener {
                            if(it.exists())
                            {

                                var us=it.data!!.get("username")
                                var pa=it.data!!.get("password")

                                if(us!!.equals(user) && pa!!.equals(pass))
                                {
                                    //Login
                                    loginEmail(user,pass)

                                }else
                                {
                                    Toast.makeText(context,"Correo o Contraseña Incorrecto!",
                                        Toast.LENGTH_SHORT).show()
                                }

                                //Log.e("DATA", "user: ${it.data}")
                                //Log.e("DATA","user: ${it.data!!.get("username")}")
                                //Log.e("DATA","pass: ${it.data!!.get("password")}")

                            }else
                            {
                                Toast.makeText(context,"Usuario No Registrado",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
            }
        }

    }

    private fun loginEmail(user: String, pass: String) {

        mAuth.login(user, pass).addOnCompleteListener { task->
            if (task.isSuccessful) {

                Log.e("TAG", "Iniciando Session!")
                var intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("username",user)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "signInWithEmail:failure", task.exception)
                Toast.makeText(context, "Error, al iniciar session", Toast.LENGTH_SHORT).show()

            }
        }

    }

    companion object{

        var mUserProvider = UserProvider()
        lateinit var mAuth:AuthProvider

    }

}