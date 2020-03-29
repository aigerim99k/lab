package com.example.lab6.autorization


import android.content.Context
import android.content.SharedPreferences
import com.example.lab6.R


class SharedPreferenceConfig(private val context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(context.resources.getString(R.string.login_preference), Context.MODE_PRIVATE)
    }

    fun writeLoginStatus(status: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(context.resources.getString(R.string.login_status_preference), status)
        editor.apply();
    }

    fun readLoginStatus(): Boolean {
        var status = false
        status = sharedPreferences.getBoolean(context.resources.getString(R.string.login_status_preference), false)
        return status

    }


}
