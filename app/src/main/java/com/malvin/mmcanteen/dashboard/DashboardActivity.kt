package com.malvin.mmcanteen.dashboard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.changepassword.ChangePasswordActivity
import com.malvin.mmcanteen.login.LoginActivity
import com.malvin.mmcanteen.scan.*
import com.malvin.mmcanteen.student.StudentListActivity
import com.malvin.mmcanteen.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()
        val session = SessionManagement(this)
        val username = session.checkData(session.keyUsername)?.capitalize()
        welcome_text?.text = resources.getString(R.string.welcome,username)
        button_logout?.setOnClickListener {
            session.clearData()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        menu_change_password?.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        menu_morning_check?.setOnClickListener {
            startActivity(Intent(this, MorningScanActivity::class.java))
        }
        menu_noon_check?.setOnClickListener {
            startActivity(Intent(this,NoonScanActivity::class.java))
        }
        menu_morning_active?.setOnClickListener {
            startActivity(Intent(this,MorningActiveScanActivity::class.java))
        }
        menu_noon_active?.setOnClickListener {
            startActivity(Intent(this,NoonActiveScanActivity::class.java))
        }
        menu_morning_nonactive?.setOnClickListener {
            startActivity(Intent(this,MorningNonActiveScanActivity::class.java))
        }
        menu_noon_nonactive?.setOnClickListener {
            startActivity(Intent(this,NoonNonActiveScanActivity::class.java))
        }
        menu_student_list?.setOnClickListener {
            startActivity(Intent(this,StudentListActivity::class.java))
        }
    }
}