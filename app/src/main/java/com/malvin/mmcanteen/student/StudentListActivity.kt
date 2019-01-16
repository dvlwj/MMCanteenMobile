package com.malvin.mmcanteen.student

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.malvin.mmcanteen.R

class StudentListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_list_student)
    }
}