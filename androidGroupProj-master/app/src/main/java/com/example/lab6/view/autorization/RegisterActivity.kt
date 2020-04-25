package com.example.lab6.view.autorization

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.lab6.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private val activity: AppCompatActivity = this@RegisterActivity
    private var ScrollView: ScrollView? = null
    private var textInputLayoutName: TextInputLayout? = null
    private var textInputLayoutEmail: TextInputLayout? = null
    private var textInputLayoutPassword: TextInputLayout? = null
    private var textInputLayoutConfirmPassword: TextInputLayout? = null
    private var textInputEditTextName: TextInputEditText? = null
    private var textInputEditTextEmail: TextInputEditText? = null
    private var textInputEditTextPassword: TextInputEditText? = null
    private var textInputEditTextConfirmPassword: TextInputEditText? = null
    private var appCompatButtonRegister: AppCompatButton? = null
    private var appCompatTextViewLoginLink: AppCompatTextView? = null
    private var inputValidation: InputValidation? = null
    private var databaseHelper: DatabaseHelper? = null
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
//        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()
    }

    private fun initViews() {
        ScrollView = findViewById(R.id.nestedScrollView)
        textInputLayoutName = findViewById<View>(R.id.textInputLayoutName) as TextInputLayout
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutConfirmPassword = findViewById<View>(R.id.textInputLayoutConfirmPassword) as TextInputLayout
        textInputEditTextName = findViewById<View>(R.id.textInputEditTextName) as TextInputEditText
        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        textInputEditTextConfirmPassword = findViewById<View>(R.id.textInputEditTextConfirmPassword) as TextInputEditText
        appCompatButtonRegister = findViewById<View>(R.id.appCompatButtonRegister) as AppCompatButton
        appCompatTextViewLoginLink = findViewById<View>(R.id.appCompatTextViewLoginLink) as AppCompatTextView
    }

    private fun initListeners() {
        appCompatButtonRegister!!.setOnClickListener(this)
        appCompatTextViewLoginLink!!.setOnClickListener(this)
    }

    private fun initObjects() {
        inputValidation = InputValidation(activity)
        databaseHelper = DatabaseHelper(activity)
        user = User()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.appCompatButtonRegister -> postDataToSQLite()
            R.id.appCompatTextViewLoginLink -> finish()
        }
    }

        private fun postDataToSQLite() {
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextName!!, textInputLayoutName!!, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextPassword!!, textInputLayoutPassword!!, getString(R.string.error_message_password))) {
            return
        }
        if (!inputValidation!!.isInputEditTextMatches(textInputEditTextPassword!!, textInputEditTextConfirmPassword!!,
                textInputLayoutConfirmPassword!!, getString(R.string.error_password_match))) {
            return
        }
        if (!databaseHelper!!.checkUser(textInputEditTextEmail!!.text.toString().trim { it <= ' ' })) {
            user!!.name = textInputEditTextName!!.text.toString().trim { it <= ' ' }
            user!!.email = textInputEditTextEmail!!.text.toString().trim { it <= ' ' }
            user!!.password = textInputEditTextPassword!!.text.toString().trim { it <= ' ' }
            databaseHelper!!.addUser(user!!)

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(ScrollView!!, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(ScrollView!!, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()

        }
    }

    private fun emptyInputEditText() {
        textInputEditTextName!!.setText(null)
        textInputEditTextEmail!!.setText(null)
        textInputEditTextPassword!!.setText(null)
        textInputEditTextConfirmPassword!!.setText(null)
    }
}