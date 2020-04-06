package com.example.lab6.autorization

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by delaroy on 3/26/18.
 */
object PreferenceUtils {
    fun saveEmail(email: String?, context: Context?): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.putString(Constants.KEY_EMAIL, email)
        prefsEditor.apply()
        return true
    }

    fun getEmail(context: Context?): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(Constants.KEY_EMAIL, null)
    }

    fun savePassword(password: String?, context: Context?): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.putString(Constants.KEY_PASSWORD, password)
        prefsEditor.apply()
        return true
    }

    fun getPassword(context: Context?): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(Constants.KEY_PASSWORD, null)
    }
}