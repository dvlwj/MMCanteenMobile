package com.malvin.mmcanteen.view.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.model.login.LoginModel
import com.malvin.mmcanteen.presenter.login.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView{

    private val presenter = LoginPresenter(this, LoginModel())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        actionBar.hide()
        button_login.setOnClickListener{validateCredentials()}
    }

    override fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    override fun setUsernameError() {
        username.error = getString(R.string.username_error)
    }

    override fun setPasswordError() {
        password.error = getString(R.string.password_error)
    }

    override fun navigateToHome() {
//        startActivity(Intent(this, MainActivity::class.java))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun validateCredentials(){
        presenter.validateCredentials(username.text.toString(), password.text.toString())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}