package com.malvin.mmcanteen.model.login

class LoginModel {
    interface OnLoginFinishedListener {
        fun onUsernameError()
        fun onPasswordError()
        fun onSuccess()
    }
    fun login(username: String, password: String, listener: OnLoginFinishedListener){
        when {
            username.isEmpty() -> listener.onUsernameError()
            password.isEmpty() -> listener.onPasswordError()
            else -> listener.onSuccess()
        }
    }
}