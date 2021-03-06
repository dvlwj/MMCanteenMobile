package com.kantinmaitreya.meilvin.serversetting

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import com.kantinmaitreya.meilvin.R
import com.kantinmaitreya.meilvin.utility.FeedbackManagement
import com.kantinmaitreya.meilvin.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_server_config.*

class ServerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_config)
        val session = SessionManagement(this)
        val serverAddress = session.checkServerAddress(session.keyServerAddress)
        server.setText(serverAddress)
        button_confirm?.setOnClickListener { validateServer() }
    }

    private fun validateServer() {
        val serverText = server?.text.toString()
        val session = SessionManagement(this)
        val fbM = FeedbackManagement(this)
        val colorWarning = ContextCompat.getColor(this, R.color.colorWarning)
        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)
        val title = resources.getString(R.string.failed_save)
        val message = resources.getString(R.string.try_again)
        val yes = resources.getString(R.string.yes)
        val serverAddress = session.checkServerAddress(session.keyServerAddress)
        when {
            serverText == serverAddress -> {
                DrawableCompat.setTint(server.background, colorWarning)
                fbM.showToastShort(resources.getString(R.string.server_same))
                server?.requestFocus()
            }
            serverText.isEmpty() -> {
                DrawableCompat.setTint(server.background, colorWarning)
                fbM.showToastShort(resources.getString(R.string.server_empty))
                server?.requestFocus()
            }
            !serverText.isEmpty() -> {
                DrawableCompat.setTint(server.background, colorAccent)
                try {
                    session.updateServerAddress(serverText)
                    fbM.showToastShort(resources.getString(R.string.success_save))
                    Handler().postDelayed({finish()},500)
                } catch (e: Exception) {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setCancelable(false)
                    dialog.setTitle(title)
                    dialog.setMessage(message)
                    dialog.setNeutralButton(yes) { DialogInterface, _ ->
                        DialogInterface.dismiss()
                    }
                    dialog.create().show()
                }
            }
        }
    }
}