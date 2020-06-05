package com.azerenterprise.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        btn_main_login.setOnClickListener{
            LoginAct.launchIntent(this)
        }
        btn_main_register.setOnClickListener{
            Register.launchIntent(this)
        }
    }
}