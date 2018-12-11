package com.malvin.mmcanteen.viewmodel.login

import android.databinding.ObservableField
import android.os.Handler

public class LoginViewModel {
    val content = ObservableField<String>("Hello")

    init {
        Handler().postDelayed({
            content.set("Data binding awesome !!!")
        }, 3_000)
    }
}

//class LoginViewModel(var loginView: LoginView?, var loginModel: LoginModel) : LoginModel.OnLoginFinishedListener {
//
//    fun validateCredentials(username: String, password: String) {
//        loginView?.showProgress()
//        loginModel.login(username, password)
//    }
//
//    fun onDestroy() {
//        loginView = null
//    }
//
//    override fun onUsernameError() {
//        loginView?.apply {
//            setUsernameError()
//            hideProgress()
//        }
//    }
//
//    override fun onPasswordError() {
//        loginView?.apply {
//            setUsernameError()
//            hideProgress()
//        }
//    }
//
//    override fun onSuccess(username: String,password: String) {
//        val data = listOf("name" to username,"password" to password)
//        val serverAddress = SessionManagement.
//        Fuel.post(ServerAddress.)
//        loginView?.navigateToHome()
//    }
//}