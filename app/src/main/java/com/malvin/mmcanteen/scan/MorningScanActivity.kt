package com.malvin.mmcanteen.scan

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.google.zxing.integration.android.IntentIntegrator
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.utility.FeedbackManagement
import com.malvin.mmcanteen.utility.ServerAddress
import com.malvin.mmcanteen.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_scan.*

class MorningScanActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        nis_number?.visibility = View.GONE
        scan_again?.visibility = View.GONE
        IntentIntegrator(this).setOrientationLocked(false).setBeepEnabled(true).initiateScan()
        scan_again?.setOnClickListener{IntentIntegrator(this).initiateScan()}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                nis_number?.visibility = View.VISIBLE
                nis_number?.text = resources.getString(R.string.result_null)
                scan_again?.visibility = View.VISIBLE
                FeedbackManagement(this).showToastLong("Hasil Kosong")
            } else {
               updateAbsen(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun updateAbsen(nis: String?){
        val session = SessionManagement(this)
        val token = session.checkData(session.keyToken).toString()
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.Absen}"
        val dataServer = listOf("nis" to nis,"status" to "pagi")
        val title = resources.getString(R.string.connection_failed)
        val message = resources.getString(R.string.connection_try_again)
        val yes = resources.getString(R.string.yes)
        val no = resources.getString(R.string.no)
        val fbM = FeedbackManagement(this)
        Fuel.post(address, dataServer).header("token" to token).timeout(10000).responseJson {
            _, response, result ->
            result.success {
                try {
                    val respond = String(response.data)
                    val respondData = Klaxon().parse<ScanStatus>(respond)
                    when(respondData?.status){
                        1 -> {
                            val nis_text = resources.getString(R.string.nis_number, nis)
                            nis_number?.visibility = View.VISIBLE
                            scan_again?.visibility = View.VISIBLE
                            nis_number?.text = nis_text
                            fbM.showToastLong("$nis sukses diabsen")
                        }
                        else -> {
                            val dialog = AlertDialog.Builder(this)
                            dialog.setCancelable(false)
                            dialog.setTitle(resources.getString(R.string.error))
                            dialog.setMessage(resources.getString(R.string.check_absen_error))
                            dialog.setNeutralButton(yes) { DialogInterface, _ ->
                                DialogInterface.dismiss()
                            }
                            dialog.create().show()
                            scan_again?.visibility = View.VISIBLE
                        }
                    }
                }
                catch(e: Exception){
                    val dialog = AlertDialog.Builder(this)
                    dialog.setCancelable(false)
                    dialog.setTitle(resources.getString(R.string.process_failed))
                    dialog.setMessage(message)
                    dialog.setNegativeButton(no) { DialogInterface, _ ->
                        DialogInterface.dismiss()
                        finish()
                    }
                    dialog.setPositiveButton(yes) { DialogInterface, _ ->
                        updateAbsen(nis)
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
                    updateAbsen(nis)
                    DialogInterface.dismiss()
                }
                dialog.create().show()
            }
        }
    }
}