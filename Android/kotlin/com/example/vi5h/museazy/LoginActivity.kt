package com.example.vi5h.museazy

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            performLogin()
        }
        lblReg.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin()
    {

        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()
        Log.d("LoginActivity", "Username: " + email)
        Log.d("LoginActivity", "Password: $password")

        if (email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "The Email and Password fields are Empty!", Toast.LENGTH_SHORT).show()
            return
        }

        // Firebase Authentication to check the Login details
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d("LoginActivity", "Login Done! ${it.result.user.email}")
                }
                .addOnFailureListener {
                    Log.d("LoginActivity", "Error with login! ${it.message}")
                    Toast.makeText(this, "Error with login! ${it.message}", Toast.LENGTH_LONG).show()
                }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()



    }
}
