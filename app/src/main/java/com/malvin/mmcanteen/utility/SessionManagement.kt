package com.malvin.mmcanteen.utility

import android.content.Context

class SessionManagement(context: Context?) {
    private val sharedPrefName = "MMCanteen"
    private val keyUsername    = "user_username"
    private val keyPassword    = "user_password"
    private val keyToken       = "user_token"
    private val keyRole        = "user_role"
    private val keyUserID      = "user_userid"
    private val sharedPrefs= context?.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
    private val sharedPrefsEditor= sharedPrefs?.edit()

    fun updateUsername(value: String?){
        sharedPrefsEditor?.putString(keyUsername,value)
        sharedPrefsEditor?.apply()
    }
    fun updatePassword(value: String?){
        sharedPrefsEditor?.putString(keyPassword,value)
        sharedPrefsEditor?.apply()
    }
    fun updateToken(value: String?){
        sharedPrefsEditor?.putString(keyToken,value)
        sharedPrefsEditor?.apply()
    }
    fun updateRole(value: String?){
        sharedPrefsEditor?.putString(keyRole,value)
        sharedPrefsEditor?.apply()
    }
    fun updateUserID(value: String?){
        sharedPrefsEditor?.putString(keyUserID,value)
        sharedPrefsEditor?.apply()
    }
    fun checkUsername(key: String?):String?{
        return sharedPrefs?.getString(key,null)
    }
    fun checkPassword(key: String?):String?{
        return sharedPrefs?.getString(key,null)
    }
    fun checkToken(key: String?):String?{
        return sharedPrefs?.getString(key,null)
    }
    fun checkRole(key: String?):String?{
        return sharedPrefs?.getString(key,null)
    }
    fun checkUserID(key: String?):String?{
        return sharedPrefs?.getString(key,null)
    }
    fun clearData(){
        sharedPrefsEditor?.clear()
        sharedPrefsEditor?.apply()
    }
}