package com.malvin.mmcanteen.dashboard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.login.LoginActivity
import com.malvin.mmcanteen.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_dashboard)
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.argb(0,0,0,0)))
//        supportActionBar?.title = resources.getString(R.string.app_name)
        val session = SessionManagement(this)
        val username = session.checkData(session.keyUsername)
        welcome_text?.text = resources.getString(R.string.welcome,username)
        button_logout?.setOnClickListener {
            session.clearData()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_main_setting -> {
//                startActivity(Intent(applicationContext, ServerActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}