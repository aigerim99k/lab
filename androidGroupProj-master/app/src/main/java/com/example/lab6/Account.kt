package com.example.lab6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.lab6.autorization.LoginActivity
import com.example.lab6.autorization.SharedPreferenceConfig

class Account : BaseActivity(2) {
    private var preferenceConfig: SharedPreferenceConfig? = null
    private val TAG = "AccountActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        preferenceConfig = SharedPreferenceConfig(applicationContext)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")
    }

    fun userLogOut(view: View) {
        preferenceConfig!!.writeLoginStatus(false)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
