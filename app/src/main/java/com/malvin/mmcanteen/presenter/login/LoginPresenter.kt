package com.malvin.mmcanteen.presenter.login

import com.github.kittinunf.fuel.Fuel
import com.malvin.mmcanteen.model.login.LoginModel
import com.malvin.mmcanteen.utility.ServerAddress
import com.malvin.mmcanteen.utility.SessionManagement
import com.malvin.mmcanteen.view.login.LoginView

class LoginPresenter(var loginView: LoginView?, var loginModel: LoginModel) : LoginModel.OnLoginFinishedListener {

    fun validateCredentials(username: String, password: String) {
        loginView?.showProgress()
        loginModel.login(username, password)
    }

    fun onDestroy() {
        loginView = null
    }

    override fun onUsernameError() {
        loginView?.apply {
            setUsernameError()
            hideProgress()
        }
    }

    override fun onPasswordError() {
        loginView?.apply {
            setUsernameError()
            hideProgress()
        }
    }

    override fun onSuccess(username: String,password: String) {
        val data = listOf("name" to username,"password" to password)
        val serverAddress = SessionManagement.
        Fuel.post(ServerAddress.)
        loginView?.navigateToHome()
    }
}