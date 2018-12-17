package com.malvin.mmcanteen.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.dashboard.DashboardActivity
import com.malvin.mmcanteen.serversetting.ServerActivity
import com.malvin.mmcanteen.utility.FeedbackManagement
import com.malvin.mmcanteen.utility.ServerAddress
import com.malvin.mmcanteen.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        button_login?.setOnClickListener {validateCredentials() }
        button_config?.setOnClickListener {
            startActivity(Intent(applicationContext, ServerActivity::class.java))
        }
    }

    private fun validateCredentials() {
        val usernameText = username?.text.toString()
        val passwordText = password?.text.toString()
        val fbM = FeedbackManagement(this)
        val colorWarning = ContextCompat.getColor(this, R.color.colorWarning)
        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)
        when {
            usernameText.isEmpty() -> {
                DrawableCompat.setTint(username.background, colorWarning)
                fbM.showToastShort(resources.getString(R.string.username_empty))
                username?.requestFocus()
            }
            passwordText.isEmpty() -> {
                DrawableCompat.setTint(password.background, colorWarning)
                fbM.showToastShort(resources.getString(R.string.password_empty))
                password?.requestFocus()
            }
            !usernameText.isEmpty() && !passwordText.isEmpty() -> {
                DrawableCompat.setTint(username.background, colorAccent)
                DrawableCompat.setTint(password.background, colorAccent)
                connectServer(usernameText, passwordText)
            }
        }
    }

    private fun connectServer(username: String, password: String) {
        progressbar?.visibility = View.VISIBLE
        button_login?.isEnabled = false
        val session = SessionManagement(this)
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.Login}"
        val dataServer = listOf("username" to username, "password" to password)
        val title = resources.getString(R.string.connection_failed)
        val message = resources.getString(R.string.connection_try_again)
        val yes = resources.getString(R.string.yes)
        val no = resources.getString(R.string.no)
        val fbM = FeedbackManagement(this)
        progressbar.progress = 0
        Fuel.post(address, dataServer).timeout(10000).responseJson { _, response, result ->
            result.success {
                val respond = String(response.data)
                val login = Klaxon().parse<LoginStatus>(respond)
                when (login?.status){
                    1 -> {
                        val json = Klaxon().parse<Data>(respond)
                        when {
                            json != null -> {
                                val uname = json.user.username
                                val role = json.user.role
                                val userID = json.user.id
                                val token = json.token
                                session.run {
                                    updateUsername(uname)
                                    updateRole(role)
                                    updateUserID(userID)
                                    updateToken(token)
                                }
                            }
                        }
                        startActivity(Intent(this, DashboardActivity::class.java))
                        finish()
                    }
                    0 -> {
                        fbM.showToastLong(resources.getString(R.string.login_failed))
                    }
                    else-> {
                        val dialog = AlertDialog.Builder(this)
                        dialog.setCancelable(false)
                        dialog.setTitle(resources.getString(R.string.error))
                        dialog.setMessage(resources.getString(R.string.login_error))
                        dialog.setNeutralButton(yes) { DialogInterface, _ ->
                            DialogInterface.dismiss()
                        }
                        dialog.create().show()
                    }
                }
                progressbar?.visibility = View.GONE
                button_login?.isEnabled = true
            }
            result.failure {
                progressbar?.visibility = View.GONE
                val dialog = AlertDialog.Builder(this)
                dialog.setCancelable(false)
                dialog.setTitle(title)
                dialog.setMessage(message)
                dialog.setNegativeButton(no) { DialogInterface, _ ->
                    button_login?.isEnabled = true
                    DialogInterface.dismiss()
                }
                dialog.setPositiveButton(yes) { DialogInterface, _ ->
                    connectServer(username, password)
                    DialogInterface.dismiss()
                }
                dialog.create().show()
            }
        }
    }
}