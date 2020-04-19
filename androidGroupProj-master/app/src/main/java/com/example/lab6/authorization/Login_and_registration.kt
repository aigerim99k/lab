package com.example.lab6.authorization

import com.example.lab6.R
import com.example.lab6.authorization.Registration
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.Account

class Login_and_registration : AppCompatActivity(), View.OnClickListener {
    var login: Button? = null
    var register: Button? = null
    var str_login_test: String? = null
    var sh: SharedPreferences? = null
    var editor: Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_registration)
        sh = getSharedPreferences("myprefe", 0)
        editor = sh?.edit()
        login = findViewById(R.id.btn_login)
        register = findViewById(R.id.btn_register)
        login?.setOnClickListener(this)
        register?.setOnClickListener(this)

        str_login_test = sh?.getString("loginTest", null)
        if (intent.getBooleanExtra("EXIT", false)) {
            finish()
            return
        }
        if (str_login_test != null
            && str_login_test.toString().trim { it <= ' ' } != "") {
            val send = Intent(applicationContext,
                Account::class.java)
            startActivity(send)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                val login = Intent(applicationContext, Login::class.java)
                startActivity(login)
            }
            R.id.btn_register -> {
                val registeration = Intent(applicationContext,
                    Registration::class.java)
                startActivity(registeration)
            }
        }
    }

    companion object {
        var str_login_test: String? = null
        @JvmField
        var sh: SharedPreferences? = null
        @JvmField
        var editor: Editor? = null
    }
}
