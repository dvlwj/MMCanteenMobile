package com.malvin.mmcanteen.report

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_report_student.*
import java.util.*




class ReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_report_student)
        search_button?.isEnabled = false
        val session = SessionManagement(this)
        val token = session.checkData(session.keyToken).toString()
        val nis = intent?.getStringExtra("NIS")
        list_report?.visibility = View.GONE
        date_time?.visibility = View.GONE
        prepareDate()
        datePicker()
//        FeedbackManagement(this).showToastLong("$nis dan $token")
        date_time?.setOnClickListener {
//            val newCalendar = Calendar.getInstance()
//            val outputDateFormatInside = SimpleDateFormat("yyyy-MM-dd",Locale.US)
//            val indonesiaDateFormat = Locale("in","ID","ID")
//            Locale.setDefault(indonesiaDateFormat)
//            val dateTimeFormatInside = SimpleDateFormat("MMMM yyyy", indonesiaDateFormat)
//            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
//                _, year, monthOfYear, _ ->
//                val newDate = Calendar.getInstance()
//                newDate.set(year, monthOfYear)
//                date_time?.text = dateTimeFormatInside.format(newDate.time)
//            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH))
//            datePicker.datePicker.minDate = newCalendar.timeInMillis
//            datePicker.show()
        }
        search_button?.setOnClickListener {
//            val kelasID = (spinner_kelas?.selectedItem as Kelas).id
//            val tahunAjaranID = (spinner_tahun_ajaran?.selectedItem as TahunAjaran).id
//            loadData(token, kelasID, tahunAjaranID)
        }
    }

    private fun prepareDate(){
        val indonesiaDateFormat = Locale("in","ID","ID")
        val calendar = Calendar.getInstance()
        val month = calendar.getDisplayName(Calendar.MONTH,Calendar.LONG,indonesiaDateFormat)
        val year = calendar.get(Calendar.YEAR)
        date_time?.text = resources.getString(R.string.report_date_time,month,year)
        date_time?.visibility = View.VISIBLE
    }

    fun datePicker() {
    }
}