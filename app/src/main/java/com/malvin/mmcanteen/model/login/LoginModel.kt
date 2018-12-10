package com.malvin.mmcanteen.model.login

class LoginModel {
    interface OnLoginFinishedListener {
        fun onUsernameError()
        fun onPasswordError()
        fun onSuccess(username: String,password: String)
    }

    fun login(username: String, password: String, listener: OnLoginFinishedListener) {
        when {
            username.isEmpty() -> listener.onUsernameError()
            password.isEmpty() -> listener.onPasswordError()
            else -> listener.onSuccess(username,password)
        }
    }
}