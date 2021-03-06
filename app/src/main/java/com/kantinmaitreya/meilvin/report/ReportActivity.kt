package com.kantinmaitreya.meilvin.report

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.kantinmaitreya.meilvin.R
import com.kantinmaitreya.meilvin.utility.FeedbackManagement
import com.kantinmaitreya.meilvin.utility.ProgressBarAnimation
import com.kantinmaitreya.meilvin.utility.ServerAddress
import com.kantinmaitreya.meilvin.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_report_student.*
import java.text.NumberFormat
import java.util.*


class ReportActivity : AppCompatActivity() {

    private var reportArrayList: ArrayList<ReportModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_report_student)
        search_button?.isEnabled = false
        val nis = intent?.getStringExtra("NIS")
        val nohp = intent?.getStringExtra("NOHP")
        list_report?.visibility = View.GONE
        date_year?.visibility = View.GONE
        dateYear()
        search_button?.setOnClickListener { searchData(nis,nohp) }
        phone_number?.setOnClickListener { callPhone(nohp)}
    }

    private fun dateYear() {
        val years = ArrayList<String>()
        val thisMonth = Calendar.getInstance().get(Calendar.MONTH)
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        val previousYear = thisYear - 5
        val nextYear = thisYear + 5
        for (i in previousYear..nextYear) years.add(Integer.toString(i))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        val spinYear = date_year
        spinYear.adapter = adapter
        spinYear?.setSelection(5)
        date_month?.setSelection(thisMonth)
        spinYear?.visibility = View.VISIBLE
        search_button?.isEnabled = true
    }

    private fun callPhone(nohp: String?){
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$nohp")
        this.startActivity(callIntent)
    }

    private fun searchData(nis: String?,nohp: String?) {
        search_button?.isEnabled = false
        val month = date_month?.selectedItemPosition?.plus(1)
        val year = date_year?.selectedItem?.toString()
        progressbar?.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progressbar, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progressbar.startAnimation(anim)
        val session = SessionManagement(this)
        val token = session.checkData(session.keyToken).toString()
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.LaporanMakan}"
        val dataServer = listOf("nis" to nis, "bulan" to month, "tahun" to year)
        val title = resources.getString(R.string.connection_failed)
        val message = resources.getString(R.string.connection_try_again)
        val yes = resources.getString(R.string.yes)
        val no = resources.getString(R.string.no)
        Fuel.post(address, dataServer).header("token" to token).timeout(10000).responseJson {
            _, response, result ->
            result.success {
                val respond = String(response.data)
                val stringBuilder = StringBuilder(respond)
                try {
                    val respondParser = Parser().parse(stringBuilder) as JsonObject
                    val respondStatus = respondParser.int("status")
                    when(respondStatus){
                        1 -> {
                            val respondPeriod = respondParser.string("periode")
                            val respondTotal = respondParser.int("total")
                            val respondReport = respondParser.array<JsonObject>("report")
                            val arrayList = respondReport?.map { it ->
                                ReportModel(
                                    it.string("time"),
                                    it.string("status"),
                                    it.string("harga")
                                )
                            }
                            reportArrayList = ArrayList(arrayList)
                            val recyclerView = list_report
                            val adapter = ReportAdapter(reportArrayList)
                            val layoutManager = LinearLayoutManager(this)
                            recyclerView?.layoutManager = layoutManager
                            recyclerView?.adapter = adapter
                            period_report?.text = resources.getString(R.string.period,respondPeriod)
                            val currency =  NumberFormat.getNumberInstance(Locale.US).format(respondTotal)
                            total_report?.text = resources.getString(R.string.total,currency)
                            phone_number?.text = nohp
                            period_report?.visibility = View.VISIBLE
                            list_report?.visibility = View.VISIBLE
                            total_report?.visibility = View.VISIBLE
                            card_report?.visibility = View.VISIBLE
                        }
                        0 -> {
                            FeedbackManagement(this).showToastLong("Tidak ada laporan")
                        }
                        else -> {
                            val dialog = AlertDialog.Builder(this)
                            dialog.setCancelable(false)
                            dialog.setTitle(resources.getString(R.string.error))
                            dialog.setMessage(resources.getString(R.string.connection_failed))
                            dialog.setNeutralButton(yes) { DialogInterface, _ ->
                                DialogInterface.dismiss()
                            }
                            dialog.create().show()
                        }
                    }
                    search_button?.isEnabled = true
                    progressbar?.visibility = View.GONE
                }
                catch (e: Exception) {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setCancelable(false)
                    dialog.setTitle(resources.getString(R.string.process_failed))
                    dialog.setMessage(message)
                    dialog.setNegativeButton(no) { DialogInterface, _ ->
                        DialogInterface.dismiss()
                        finish()
                    }
                    dialog.setPositiveButton(yes) { DialogInterface, _ ->
                        searchData(nis,nohp)
                        DialogInterface.dismiss()
                    }
                    dialog.create().show()
                }
            }
            result.failure {
                val dialog = AlertDialog.Builder(this)
                dialog.setCancelable(false)
                dialog.setTitle(title)
                dialog.setMessage(message)
                dialog.setNegativeButton(no) { DialogInterface, _ ->
                    DialogInterface.dismiss()
                    finish()
                }
                dialog.setPositiveButton(yes) { DialogInterface, _ ->
                    searchData(nis,nohp)
                    DialogInterface.dismiss()
                }
                dialog.create().show()
            }
        }
    }
}