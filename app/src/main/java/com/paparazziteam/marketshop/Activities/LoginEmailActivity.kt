package com.paparazziteam.marketshop.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.paparazziteam.marketshop.Providers.AuthProvider
import com.paparazziteam.marketshop.Providers.UserProvider
import com.paparazziteam.marketshop.databinding.ActivityLoginEmailBinding

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding

    var mUserProvider = UserProvider()
    var mAuth = AuthProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        setOnClickListener()




    }

    private fun setOnClickListener() {

        binding.circularImageviewBack.setOnClickListener {
            finish()
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
                                    Toast.makeText(this@LoginEmailActivity,"Correo o Contraseña Incorrecto!",Toast.LENGTH_SHORT).show()
                                }

                                //Log.e("DATA", "user: ${it.data}")
                                //Log.e("DATA","user: ${it.data!!.get("username")}")
                                //Log.e("DATA","pass: ${it.data!!.get("password")}")

                            }else
                            {
                                Toast.makeText(this@LoginEmailActivity,"Usuario No Registrado",Toast.LENGTH_SHORT).show()
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
                // Sign in success, update UI with the signed-in user's information
                Log.e("TAG", "Iniciando Session!")
                //val user = auth.currentUser
                //updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Error, al iniciar session", Toast.LENGTH_SHORT).show()

            }
        }

    }
}