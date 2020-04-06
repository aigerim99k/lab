package com.example.lab6.autorization

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.example.lab6.R
import com.example.lab6.autorization.PreferenceUtils.saveEmail
import com.example.lab6.autorization.PreferenceUtils.savePassword
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.lab6.Account



class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val activity: AppCompatActivity = this@LoginActivity
    private var nestedScrollView: NestedScrollView? = null
    private var textInputLayoutEmail: TextInputLayout? = null
    private var textInputLayoutPassword: TextInputLayout? = null
    private var textInputEditTextEmail: TextInputEditText? = null
    private var textInputEditTextPassword: TextInputEditText? = null
    private var appCompatButtonLogin: AppCompatButton? = null
    private var textViewLinkRegister: AppCompatTextView? = null
    private var textViewLinkForgotPassword: AppCompatTextView? = null
    private var inputValidation: InputValidation? = null
    private var databaseHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //        getSupportActionBar().hide();
        initViews()
        initListeners()
        initObjects()
    }

    private fun initViews() {
//        nestedScrollView = findViewById(R.id.nestedScrollView)
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        appCompatButtonLogin = findViewById<View>(R.id.appCompatButtonLogin) as AppCompatButton
        textViewLinkRegister = findViewById<View>(R.id.textViewLinkRegister) as AppCompatTextView
        textViewLinkForgotPassword = findViewById<View>(R.id.forgotPassword) as AppCompatTextView
        val utils = PreferenceUtils
        if (utils.getEmail(this) != null) {
            val intent = Intent(this@LoginActivity, Account::class.java)
            startActivity(intent)
        } else {
        }
    }

    private fun initListeners() {
        appCompatButtonLogin!!.setOnClickListener(this)
        textViewLinkRegister!!.setOnClickListener(this)
        textViewLinkForgotPassword!!.setOnClickListener(this)
    }

    private fun initObjects() {
        databaseHelper = DatabaseHelper(activity)
        inputValidation = InputValidation(activity)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.appCompatButtonLogin -> verifyFromSQLite()
            R.id.textViewLinkRegister -> {
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }

    private fun verifyFromSQLite() {
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextPassword!!, textInputLayoutPassword!!, getString(R.string.error_message_email))) {
            return
        }
        val email = textInputEditTextEmail!!.text.toString().trim { it <= ' ' }
        val password = textInputEditTextPassword!!.text.toString().trim { it <= ' ' }
        if (databaseHelper!!.checkUser(email, password)) {
            saveEmail(email, this)
            savePassword(password, this)
            val accountsIntent = Intent(activity, Account::class.java)
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
            emptyInputEditText()
            startActivity(accountsIntent)
            finish()
        } else {
            Snackbar.make(nestedScrollView!!, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun emptyInputEditText() {
        textInputEditTextEmail!!.setText(null)
        textInputEditTextPassword!!.setText(null)
    }
}