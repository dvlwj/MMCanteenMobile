package com.kantinmaitreya.meilvin.changepassword

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.kantinmaitreya.meilvin.R
import com.kantinmaitreya.meilvin.utility.FeedbackManagement
import com.kantinmaitreya.meilvin.utility.ProgressBarAnimation
import com.kantinmaitreya.meilvin.utility.ServerAddress
import com.kantinmaitreya.meilvin.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        button_confirm?.setOnClickListener {
            validatePassword()
        }
    }
    private fun validatePassword(){
        val password = field_new_password?.text.toString()
        val fbM = FeedbackManagement(this)
        when {
            password.isEmpty() -> {
                fbM.showToastShort(resources.getString(R.string.change_password_null))
                field_new_password?.requestFocus()
            }
            password.count() < 6 ->{
                fbM.showToastShort(resources.getString(R.string.change_password_under6))
                field_new_password?.requestFocus()
            }
            else -> {
                updatePassword(password)
            }
        }
    }
    private fun updatePassword(password: String?){
        progressbar?.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progressbar, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progressbar.startAnimation(anim)
        button_confirm?.isEnabled = false
        val session = SessionManagement(this)
        val token = session.checkData(session.keyToken).toString()
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.Petugas}"
        val dataServer = listOf("password" to password)
        val title = resources.getString(R.string.connection_failed)
        val message = resources.getString(R.string.connection_try_again)
        val yes = resources.getString(R.string.yes)
        val no = resources.getString(R.string.no)
        val fbM = FeedbackManagement(this)
        Fuel.patch(address, dataServer).header("token" to token).timeout(10000).responseJson { _, response, result ->
            result.success {
                try {
                    val respond = String(response.data)
                    val changePassword = Klaxon().parse<ChangePasswordStatus>(respond)
                    when (changePassword?.status) {
                        1 -> {
                            fbM.showToastShort(resources.getString(R.string.change_password_success))
                            finish()
                        }
                        2 -> {
                            fbM.showToastShort(resources.getString(R.string.change_password_under6))
                            field_new_password?.requestFocus()
                        }
                        else -> {
                            val dialog = AlertDialog.Builder(this)
                            dialog.setCancelable(false)
                            dialog.setTitle(resources.getString(R.string.error))
                            dialog.setMessage(resources.getString(R.string.change_password_error))
                            dialog.setNeutralButton(yes) { DialogInterface, _ ->
                                DialogInterface.dismiss()
                            }
                            dialog.create().show()
                        }
                    }
                }
                catch (e: Exception){
                    val dialog = AlertDialog.Builder(this)
                    dialog.setCancelable(false)
                    dialog.setTitle(resources.getString(R.string.process_failed))
                    dialog.setMessage(message)
                    dialog.setNegativeButton(no) { DialogInterface, _ ->
                        DialogInterface.dismiss()
                        finish()
                    }
                    dialog.setPositiveButton(yes) { DialogInterface, _ ->
                        updatePassword(password)
                        DialogInterface.dismiss()
                    }
                    dialog.create().show()
                }
                progressbar?.visibility = View.GONE
                button_confirm?.isEnabled = true
            }
            result.failure {
                progressbar?.visibility = View.GONE
                val dialog = AlertDialog.Builder(this)
                dialog.setCancelable(false)
                dialog.setTitle(title)
                dialog.setMessage(message)
                dialog.setNegativeButton(no) { DialogInterface, _ ->
                    button_confirm?.isEnabled = true
                    DialogInterface.dismiss()
                }
                dialog.setPositiveButton(yes) { DialogInterface, _ ->
                    updatePassword(password)
                    DialogInterface.dismiss()
                }
                dialog.create().show()
            }
        }
    }
}