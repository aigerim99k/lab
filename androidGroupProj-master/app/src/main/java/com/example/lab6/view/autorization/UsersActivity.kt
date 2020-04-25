package com.example.lab6.view.autorization

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.R

class UsersActivity : AppCompatActivity() {
    private var textViewName: TextView? = null
    var logout: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        logout = findViewById(R.id.logout)
        textViewName = findViewById<View>(R.id.text1) as TextView
        val intent = intent
        if (intent.hasExtra("EMAIL")) {
            val nameFromIntent = getIntent().getStringExtra("EMAIL")
            textViewName!!.text = " $nameFromIntent"
        } else {
            val email = PreferenceUtils.getEmail(this)
            textViewName!!.text = "Авторизация"
        }
        logout!!.setOnClickListener(View.OnClickListener {
            PreferenceUtils.savePassword(null, this@UsersActivity)
            PreferenceUtils.saveEmail(null, this@UsersActivity)
            val intent = Intent(this@UsersActivity, UsersActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}