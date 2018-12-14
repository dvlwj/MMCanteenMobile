package com.malvin.mmcanteen.utility

import android.content.Context

class SessionManagement(context: Context?) {

    private val sharedPrefName   = "MMCanteen"
    val keyUsername      = "user_username"
    val keyPassword      = "user_password"
    val keyToken         = "user_token"
    val keyRole          = "user_role"
    val keyUserID        = "user_userid"
    val keyServerAddress = "server_address"
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
    fun updateServerAddress(value: String?){
        sharedPrefsEditor?.putString(keyServerAddress,value)
        sharedPrefsEditor?.apply()
    }
    fun checkData(key: String?):String?{
        return sharedPrefs?.getString(key,null)
    }
    fun checkServerAddress(key: String?):String?{
//        return sharedPrefs?.getString(key,"192.168.1.113")
        return sharedPrefs?.getString(key,ServerAddress.localhost)
    }
    fun clearData(){
        sharedPrefsEditor?.clear()
        sharedPrefsEditor?.apply()
    }
}