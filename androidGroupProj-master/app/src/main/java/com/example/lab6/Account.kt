package com.example.lab6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.lab6.authorization.Login
import com.example.lab6.authorization.Login_and_registration


class Account : BaseActivity(2), View.OnClickListener {
    private val TAG = "AccountActivity"
    var str_getName: String? = null
    var profile: TextView? = null
    var logout: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")
        str_getName = Login_and_registration.sh!!.getString("name", null)
        profile = findViewById(R.id.txt_profile)
        logout = findViewById(R.id.logout)
        logout?.setOnClickListener(this)

        profile?.setText(str_getName)

    }
    override fun onClick(v: View) {
        Toast.makeText(applicationContext, "You have successfully logout",
            Toast.LENGTH_LONG).show()
        Login_and_registration.editor!!.remove("loginTest")
        Login_and_registration.editor!!.commit()
        val sendToLoginandRegistration = Intent(applicationContext,
            Login::class.java)
        startActivity(sendToLoginandRegistration)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@Account,
                Login_and_registration::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        }
        return super.onKeyDown(keyCode, event)
    }
}
