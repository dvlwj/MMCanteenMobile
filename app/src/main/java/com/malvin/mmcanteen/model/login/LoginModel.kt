package com.malvin.mmcanteen.model.login

import java.util.*

//class LoginModel {
//    interface OnLoginFinishedListener {
//        fun onUsernameError()
//        fun onPasswordError()
//        fun onSuccess(username: String,password: String)
//    }
//
//    fun login(username: String, password: String, listener: OnLoginFinishedListener) {
//        when {
//            username.isEmpty() -> listener.onUsernameError()
//            password.isEmpty() -> listener.onPasswordError()
//            else -> listener.onSuccess(username,password)
//        }
//    }
//}
class LoginModel: Observable(){
    /// The first name of the user
    var username: String = ""
        set(value) {
            field = value
            setChangedAndNotify("username")
        }

    /// The last name of the user
    var password: String = ""
        set(value) {
            field = value
            setChangedAndNotify("password")
        }

    private fun setChangedAndNotify(field: Any)
    {
        setChanged()
        notifyObservers(field)
    }
}