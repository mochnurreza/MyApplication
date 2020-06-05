package com.azerenterprise.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class Register : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val storage = FirebaseStorage.getInstance()
    val pick_photo = 100
    var photo_uri : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
    }

    private fun initView() {
        btn_register_user.setOnClickListener{
            registerUserToFirebase()
        }
        iv_register_photo_profile.setOnClickListener{
            getPhotoFromPhone()
        }
    }

    private fun getPhotoFromPhone() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,pick_photo)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == pick_photo){
            if (resultCode == Activity.RESULT_OK && data!!.data != null) {
                photo_uri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap( contentResolver, photo_uri)
                iv_register_photo_profile.setImageBitmap(bitmap)
            }
        }
    }

    private fun registerUserToFirebase() {
        auth.createUserWithEmailAndPassword(txt_email.text.toString(), txt_password.text.toString())
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "User berhasil dibuat", Toast.LENGTH_LONG).show()
                    uploadPhotoToFirebase()
                }else{
                    Toast.makeText(this, it.result.toString(), Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
            }

    }

    private fun uploadPhotoToFirebase() {
        val photoName = UUID.randomUUID().toString()
        val uploadFirebase = FirebaseStorage.getInstance().getReference("sc/images/$photoName")
        uploadFirebase.putFile(photo_uri!!)
            .addOnSuccessListener {
                uploadFirebase.downloadUrl.addOnSuccessListener{
                    Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
//data user
                    saveAllUserDataToDatabase(it.toString())
                }
            }
    }

    private fun saveAllUserDataToDatabase(photoURL :String) {
        val uid = FirebaseAuth.getInstance().uid
        val db = FirebaseDatabase.getInstance().getReference("user/$uid")

        db.setValue(User(uid.toString(), txt_nama.text.toString(), photoURL, txt_email.text.toString()))
            .addOnSuccessListener {
                Toast.makeText(this,"Data sudah disimpan", Toast.LENGTH_LONG).show()
                Home.launchIntent(this)
            }
            .addOnFailureListener{

            }
    }


    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, Register::class.java)
            context.startActivity(intent)
        }
    }
}
