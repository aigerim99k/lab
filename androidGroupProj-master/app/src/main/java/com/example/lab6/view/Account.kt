package com.example.lab6.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.lab6.BaseActivity
import com.example.lab6.R
import com.example.lab6.view.autorization.LoginActivity
import com.example.lab6.view.autorization.PreferenceUtils


class Account : BaseActivity(2) {
    private val TAG = "AccountActivity"
    private var textViewName: TextView? = null
    var logout: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")
        logout = findViewById(R.id.logout)
        textViewName = findViewById<View>(R.id.profileText) as TextView



        val intent = intent
        if (intent.hasExtra("EMAIL")) {
            val nameFromIntent = getIntent().getStringExtra("EMAIL")
            textViewName!!.setText("$nameFromIntent");
        } else {
            val email = PreferenceUtils.getEmail(this)
            textViewName!!.text = "Профиль"
        }
        logout!!.setOnClickListener(View.OnClickListener {
            PreferenceUtils.savePassword(null, this@Account)
            PreferenceUtils.saveEmail(null, this@Account)
            val intent = Intent(this@Account, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}
