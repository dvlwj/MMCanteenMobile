package com.malvin.mmcanteen.presenter.login

import com.malvin.mmcanteen.model.login.LoginModel
import com.malvin.mmcanteen.view.login.LoginView

abstract class LoginPresenter(var loginView: LoginView?, var loginModel: LoginModel ) : LoginModel.OnLoginFinishedListener {

    fun validateCredentials(username: String, password: String){
        loginView?.showProgress()
        loginModel.login(username,password,this)
    }
    fun onDestroy() {
        loginView = null
    }
}