package com.example.lab6.authorization
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.R
import com.example.lab6.authorization.Login_and_registration

class Registration : AppCompatActivity(), View.OnClickListener {
    var register: Button? = null
    var backlink: TextView? = null
    var str_Name: String? = null
    var str_Password: String? = null
    var str_RePassword: String? = null
    var str_Email: String? = null
    var edt_Name: EditText? = null
    var edt_Password: EditText? = null
    var edt_RePassword: EditText? = null
    var edt_Email: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        register = findViewById(R.id.btn_register)
        edt_Name = findViewById(R.id.edt_Rname)
        edt_Password = findViewById(R.id.edt_Rpassword)
        edt_RePassword = findViewById(R.id.edt_RRepassword)
        edt_Email = findViewById(R.id.edt_email)
        register?.setOnClickListener(this)
        backlink = findViewById(R.id.back)
        backlink?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                val backlink = Intent(applicationContext, Login::class.java)
                startActivity(backlink)
            }
        }
        str_Name = edt_Name!!.text.toString()
        str_Password = edt_Password!!.text.toString()
        str_RePassword = edt_RePassword!!.text.toString()
        str_Email = edt_Email!!.text.toString()
        if ((str_Name!!.length == 0) and (str_Password!!.length == 0
                    ) and (str_RePassword!!.length == 0)) {
            Toast.makeText(applicationContext,
                "All fields are mandatory to fill", Toast.LENGTH_LONG)
                .show()
        } else if (str_Name!!.length == 0) {
            Toast.makeText(applicationContext, "Please enter your Name",
                Toast.LENGTH_LONG).show()
        } else if (str_Password!!.length == 0) {
            Toast.makeText(applicationContext,
                "Please enter your Password", Toast.LENGTH_LONG).show()
        } else if (str_RePassword!!.length == 0) {
            Toast.makeText(applicationContext,
                "Please Re-enter your Password", Toast.LENGTH_LONG).show()
        } else if (str_Email!!.length == 0) {
            Toast.makeText(applicationContext,
                "Please enter your Email Id", Toast.LENGTH_LONG).show()
        } else if (str_Password!!.contains(str_RePassword!!) != str_RePassword!!
                .contains(str_Password!!)) {
            Toast.makeText(applicationContext,
                "Confirm Password does not match", Toast.LENGTH_LONG)
                .show()
        } else {
            Login_and_registration.editor?.putString("name", str_Name)
            Login_and_registration.editor?.putString("password", str_RePassword)
            Login_and_registration.editor?.putString("email", str_Email)
            Login_and_registration.editor?.commit()
            val sendtoLogin = Intent(applicationContext,
                Login::class.java)
            Toast.makeText(applicationContext,
                "You have successfully registered", Toast.LENGTH_LONG)
                .show()
            startActivity(sendtoLogin)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@Registration, Login_and_registration::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        }
        return super.onKeyDown(keyCode, event)
    }
}