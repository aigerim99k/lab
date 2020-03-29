package com.example.lab6.autorization


import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.lab6.R

class SuccessActivity : AppCompatActivity() {
    private var preferenceConfig: SharedPreferenceConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceConfig = SharedPreferenceConfig(applicationContext)

    }

 fun userLogOut(view: View) {
        preferenceConfig!!.writeLoginStatus(false)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
