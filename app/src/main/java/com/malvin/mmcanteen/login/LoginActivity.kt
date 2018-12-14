package com.malvin.mmcanteen.login

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.utility.FeedbackManagement
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        button_login?.setOnClickListener{validateCredentials()}
    }

    fun validateCredentials(){
        val usernameText = username?.text
        val passwordText = password?.text
        val fbM = FeedbackManagement(this)
        val colorWarning = ContextCompat.getColor(this, R.color.colorWarning)
        progressbar?.visibility = View.VISIBLE
        when {
            usernameText.isNullOrEmpty() -> {
                DrawableCompat.setTint(username.background,colorWarning)
                username.backgroundTintList
                fbM.showToastShort(R.string.username_error.toString())
            }
            passwordText.isNullOrEmpty() -> {
                DrawableCompat.setTint(password.background,colorWarning)
                fbM.showToastShort(R.string.password_error.toString())
            }
            !usernameText.isNullOrEmpty() && !passwordText.isNullOrEmpty() -> {
                fbM.showToastShort("username : $usernameText , password : $passwordText")
            }
        }
        progressbar?.visibility = View.GONE
    }
}