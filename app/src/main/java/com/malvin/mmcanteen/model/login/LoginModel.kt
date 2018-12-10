package com.malvin.mmcanteen.model.login

class LoginModel {
    interface OnLoginFinishedListener {
        fun onUsernameError()
        fun onPasswordError()
        fun onSuccess(username: String, password: String){
/*
disini gw pusing gimana ceritanya
rencana nya mau buat kayak gini
val server = SessionManagement(context).checkData(SessionManagement(context).keyServerAddress)
*/
//            val server = SessionManagement().
//            lalu bundle data untuk post nya via variabel data
            val data = listOf("name" to username, "password" to password)
//            lalu post via Fuel ke Server
//            Fuel.post(){}
        }
    }
    fun login(username: String, password: String, listener: OnLoginFinishedListener){
        when {
            username.isEmpty() -> listener.onUsernameError()
            password.isEmpty() -> listener.onPasswordError()
            else -> listener.onSuccess(username,password)
        }
    }
}