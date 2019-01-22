package com.kantinmaitreya.meilvin.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.kantinmaitreya.meilvin.R
import com.kantinmaitreya.meilvin.dashboard.DashboardActivity
import com.kantinmaitreya.meilvin.login.LoginActivity
import com.kantinmaitreya.meilvin.utility.SessionManagement

class SplashcreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar?.hide()
        val session = SessionManagement(this)
        val token = session.checkData(session.keyToken)
        when{
            token.isNullOrEmpty()-> {
                Handler().postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },2000)
            }
            !token.isNullOrEmpty() ->{
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }
    }
}