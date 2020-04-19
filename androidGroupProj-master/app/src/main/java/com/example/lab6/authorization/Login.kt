package com.example.lab6.authorization

import com.example.lab6.authorization.Registration
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.Account
import com.example.lab6.Movie.MainActivity
import com.example.lab6.R
import com.example.lab6.authorization.Login_and_registration


class Login : AppCompatActivity(), View.OnClickListener {
    var str_UserName: String? = null
    var str_Password: String? = null
    var str_getID: String? = null
    var str_getPass: String? = null
    var edt_UName: EditText? = null
    var edt_Password: EditText? = null
    var login: Button? = null
    var registerlink: TextView? = null
    var backlink: TextView? = null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)
        str_getID = Login_and_registration.sh?.getString("name", null)
        str_getPass = Login_and_registration.sh?.getString("password", null)
        login = findViewById(R.id.btn_login)
        edt_UName = findViewById(R.id.edt_userName)
        edt_Password = findViewById(R.id.edt_password)
        registerlink = findViewById(R.id.btn_register)
        backlink = findViewById(R.id.back)
        registerlink?.setOnClickListener(this)
        backlink?.setOnClickListener(this)
        login?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        str_UserName = edt_UName!!.text.toString()
        str_Password = edt_Password!!.text.toString()
        when (v.id) {
            R.id.btn_register -> {
                val registr = Intent(applicationContext, Registration::class.java)
                startActivity(registr)
            }
            R.id.back -> {
                val back = Intent(applicationContext, Login_and_registration::class.java)
                startActivity(back)
            }
        }
        if (str_UserName!!.length == 0 && str_Password!!.length == 0) {
            Toast.makeText(applicationContext,
                "Please enter your login User Name and Password",
                Toast.LENGTH_LONG).show()
        } else if (str_UserName!!.length == 0) {
            Toast.makeText(applicationContext,
                "Please enter your User Name", Toast.LENGTH_LONG).show()
        } else if (str_Password!!.length == 0) {
            Toast.makeText(applicationContext,
                "Please enter your Password", Toast.LENGTH_LONG).show()
        } else if (str_getID!!.toRegex().matches("") && str_getPass!!.toRegex().matches("")) {
            Toast.makeText(applicationContext,
                "Details does not belongs to any account",
                Toast.LENGTH_LONG).show()
        } else if (!str_UserName!!.toRegex().matches(str_getID!!)) {
            Toast.makeText(applicationContext,
                "Either login/password is incorrect", Toast.LENGTH_LONG)
                .show()
        } else if (!str_getPass!!.toRegex().matches(str_Password!!)) {
            Toast.makeText(applicationContext,
                "Either login/password is incorrect", Toast.LENGTH_LONG)
                .show()
        } else if (str_getID!!.toRegex().matches(str_UserName!!)
            && str_getPass!!.toRegex().matches(str_Password!!)) {
            Login_and_registration.editor?.putString("loginTest", "true")
            Login_and_registration.editor?.commit()
            Toast.makeText(applicationContext,
                "You have successfuly login", Toast.LENGTH_LONG).show()
            val sendToLogout = Intent(applicationContext,
                MainActivity::class.java)
            startActivity(sendToLogout)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@Login, Login_and_registration::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        }
        return super.onKeyDown(keyCode, event)
    }
}