package com.free.assignmentapplication.data.local.prefrences

import android.content.SharedPreferences

class PreferencesManager(private val sharedPreferences: SharedPreferences) {

    private val editor: SharedPreferences.Editor = this.sharedPreferences.edit()


    fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun returnString(key: String): String{
        return sharedPreferences.getString(key , "").toString()
    }

}