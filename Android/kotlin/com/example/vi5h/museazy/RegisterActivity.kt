package com.example.vi5h.museazy

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            performRegisteration()
        }

        lblRegToLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegUploadPic.setOnClickListener {
            Log.d("RegisterActivity", "Try to show the profile pic")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)
        {
            //proceed  and check what the selected image was
            Log.d("RegisterActivity", "Photo was selected")
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            imgProfilePic.setImageBitmap(bitmap)
            btnRegUploadPic.alpha = 0f
         //   val bitmapDrawable = BitmapDrawable(bitmap)
         //   btnRegUploadPic.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun performRegisteration()
    {
        val regUsername = txtRegUsername.text.toString()
        val regEmail = txtRegEmail.text.toString()
        val regPassword = txtRegPassword.text.toString()

        //Checking if the text fields are empty.
        if(regEmail.isEmpty() || regPassword.isEmpty())
        {
            Toast.makeText(this, "The Email and Password fields are Empty!", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("RegisterActivity", "Username: " + regUsername)
        Log.d("RegisterActivity", "Email: " + regEmail)
        Log.d("RegisterActivity", "Password: $regPassword")

        //Firebase Authentication to create a user with all the details provided
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(regEmail, regPassword)
                .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener

                    //else if Successful
                    Log.d("RegisterActivity", "Created user with UID: ${it.result.user.uid}")
                    uploadPhotoToFirebase()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener{
                    Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                    Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_LONG).show()
                }
    }

    private fun uploadPhotoToFirebase()
    {
        if (selectedPhotoUri == null) return
        val filname = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filname")
        ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d("RegisterActivity", "Successfully uploaded image: ${it.metadata?.path}")
                    ref.downloadUrl.addOnSuccessListener {
                        Log.d("RegisterActivity", "File Location: $it")
                        saveUserToFirebaseDatabase(it.toString())
                    }
                }
                .addOnFailureListener{
                    //do some fail stuff here
                }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String)
    {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uid, txtRegUsername.text.toString(), profileImageUrl)
        ref.setValue(user)
                .addOnSuccessListener {
                    Log.d("RegisterActivity", "Finally we saved the user to the database")
                }
    }
}

class User(val uid: String, val username: String, val profileImageUrl: String)
