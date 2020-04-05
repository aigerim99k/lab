package com.example.lab6.autorization

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.lab6.Movie.MainActivity
import com.example.lab6.R

class LoginActivity : AppCompatActivity() {
    private var preferenceConfig: SharedPreferenceConfig? = null
    private var UserName: TextView? = null
    private var UserPassword: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activityyy_main)
        preferenceConfig = SharedPreferenceConfig(applicationContext)
        UserName = findViewById(R.id.user_name)
        UserPassword = findViewById(R.id.user_password)

        if (preferenceConfig!!.readLoginStatus()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    fun loginUser(view: View) {
        val username = UserName!!.text.toString()
        val userpassword = UserPassword!!.text.toString()
        if (username == resources.getString(R.string.user_name) && userpassword == resources.getString(R.string.user_password)) {
            startActivity(Intent(this, MainActivity::class.java))
            preferenceConfig!!.writeLoginStatus(true)
            finish()
        } else {
            Toast.makeText(this, "Login Failed.Try again", Toast.LENGTH_SHORT).show()
            UserName!!.text = ""
            UserPassword!!.text = ""
        }
    }

}
