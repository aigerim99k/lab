package com.example.lab6.autorization

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.lab6.MovieApi
import com.example.lab6.RetrofitService
import com.example.lab6.json.movie.Session
import com.example.lab6.json.TokenResponse
import com.example.lab6.json.movie.Validation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var session_id : String = ""

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

    lateinit var requestToken: String
    lateinit var validation: Validation
    lateinit var requestTokenResult : TokenResponse

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

    fun getToken(){
        RetrofitService.getMovieApi(MovieApi::class.java).getNewToken(getString(R.string.api_key)).enqueue(object : Callback<TokenResponse>{
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful){
                    val result = response.body()
                    if (result!=null){
                        requestToken = result.request_token
                        validation = Validation(
                            textInputEditTextEmail?.text.toString(),
                            textInputEditTextPassword?.text.toString(),
                            requestToken
                        )
                        initValidation()
                    } else {
                        Toast.makeText(activity?.applicationContext,"TokenRequest Failure", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    fun initValidation(){
        RetrofitService.getMovieApi(MovieApi::class.java).validation(getString(R.string.api_key), validation).enqueue(object : Callback<TokenResponse>{
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful){
                    requestTokenResult = TokenResponse(requestToken)
                    initSession()
                }
                else{
                    Toast.makeText(activity?.applicationContext,"Wrong validation", Toast.LENGTH_LONG).show()
                }
            }

        })
    }
    fun initSession(){
        RetrofitService.getMovieApi(MovieApi::class.java).createSession(getString(R.string.api_key), requestTokenResult).enqueue(object : Callback<Session>{
            override fun onFailure(call: Call<Session>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<Session>, response: Response<Session>) {

            }

        })
    }
}