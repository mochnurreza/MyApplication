package com.azerenterprise.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        checkUserAccountSignIn()
    }

    private fun checkUserAccountSignIn() {
        if(FirebaseAuth.getInstance().uid.isNullOrEmpty()){
            LoginAct.launchIntentClearTask(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_sign_out ->{
                signOutUser()
            }
            R.id.nav_chat->{
                navigateUpTo()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateUpTo() {
        FirebaseAuth.getInstance()
        Friendlist.launchIntent(this)
    }

    private fun signOutUser() {
        FirebaseAuth.getInstance().signOut()
        LoginAct.launchIntentClearTask(this)
    }

    companion object{
        fun launchIntent(context: Context){
            val intent = Intent(context, Home::class.java)
            context.startActivity(intent)
        }

    }
}

