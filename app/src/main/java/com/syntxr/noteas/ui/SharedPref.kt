package com.syntxr.noteas.ui

import android.content.Context
import android.preference.PreferenceManager

class SharedPref (context: Context){

    companion object{
        private const val THEME_KEY = " THEME_VALUE"
    }

    private val currentTheme = PreferenceManager.getDefaultSharedPreferences(context)
    var theme = currentTheme.getString(THEME_KEY,"light")
    set(value) = currentTheme.edit().putString(THEME_KEY,value).apply()
}