package com.malvin.mmcanteen.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.dashboard.DashboardActivity
import com.malvin.mmcanteen.login.LoginActivity
import com.malvin.mmcanteen.utility.SessionManagement

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
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                },2000)
            }
            !token.isNullOrEmpty() ->{
                startActivity(Intent(applicationContext, DashboardActivity::class.java))
                finish()
            }
        }
    }
}