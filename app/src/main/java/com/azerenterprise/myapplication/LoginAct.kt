package com.azerenterprise.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginAct : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }
    private fun initView() {
        btn_login_login_user.setOnClickListener{
            loginToFirebase()
        }
    }
    private fun loginToFirebase() {
        val email = et_login_email.text.toString().trim()
        val password = et_login_password.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it. isSuccessful){
                    Toast.makeText(this, "Berhasil Login", Toast.LENGTH_LONG).show()
                    Home.launchIntent(this)
                }else{

                }
            }
    }
    companion object {
        fun launchIntent(context: Context) {
            val intent = Intent(context, LoginAct::class.java)
            context.startActivity(intent)
        }

        fun launchIntentClearTask(context: Context) {
            val intent = Intent(context, LoginAct::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }
}