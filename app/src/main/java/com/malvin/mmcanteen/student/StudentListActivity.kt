package com.malvin.mmcanteen.student

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.utility.ProgressBarAnimation
import com.malvin.mmcanteen.utility.ServerAddress
import com.malvin.mmcanteen.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_list_student.*

class StudentListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_list_student)
        search_button?.isEnabled = false
        val session = SessionManagement(this)
        val token = session.checkData(session.keyToken).toString()
        loadKelas(token)
        search_button?.setOnClickListener {
            val kelasID = spinner_kelas?.id
            val tahunAjaranID = spinner_tahun_ajaran?.id
            loadData(token,kelasID,tahunAjaranID)
        }
    }

    private fun loadKelas(token: String){
        progressbar?.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progressbar, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progressbar.startAnimation(anim)
        val session = SessionManagement(this)
        val title = resources.getString(R.string.connection_failed)
        val message = resources.getString(R.string.connection_try_again)
        val yes = resources.getString(R.string.yes)
        val no = resources.getString(R.string.no)
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.Kelas}"
        val spinnerKelas = spinner_kelas
        val arraySpinner = arrayOf("Loading...")
        val spinnerArray = ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpinner)
        spinnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKelas?.adapter = spinnerArray
        Fuel.get(address).header("token" to token).timeout(10000).responseJson {
            _,response,result ->
            result.success {
                try {
                    val respond = String(response.data)
                    val respondData = Klaxon().parse<Status>(respond)
                    when(respondData?.status) {
                        1 -> {
                            val json = Klaxon().parse<DataKelas>(respond)
                            when{
                                json != null -> {
                                    val idKelas = json.kelas.id
                                    val namaKelas = json.kelas.name
                                    // lanjutkan disini usahakan buat jadi array untuk membuat spinner
                                    loadTahunAjaran(token)
                                }
                            }
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
                    progressbar?.visibility = View.GONE
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
                        loadKelas(token)
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
                    loadKelas(token)
                    DialogInterface.dismiss()
                }
                dialog.create().show()
            }
        }
    }

    private fun loadTahunAjaran(token: String){
        progressbar?.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progressbar, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progressbar.startAnimation(anim)
        val session = SessionManagement(this)
        val title = resources.getString(R.string.connection_failed)
        val message = resources.getString(R.string.connection_try_again)
        val yes = resources.getString(R.string.yes)
        val no = resources.getString(R.string.no)
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.TahunAjaran}"
        val spinnerTahun = spinner_tahun_ajaran
        val arraySpinner = arrayOf("Loading...")
        val spinnerArray = ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySpinner)
        spinnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTahun?.adapter = spinnerArray
        Fuel.get(address).header("token" to token).timeout(10000).responseJson { _, response, result ->
            result.success {
                try {
                    val respond = String(response.data)
                    val respondData = Klaxon().parse<Status>(respond)
                    when(respondData?.status) {
                        1 -> {
                            val json = Klaxon().parse<DataTahunAjaran>(respond)
                            when{
                                json != null -> {
                                    val idTahunAjaran = json.th_ajaran.id
                                    val namaTahunAjaran = json.th_ajaran.tahun
                                    // lanjutkan disini usahakan buat jadi array untuk membuat spinner
                                }
                            }
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
                    progressbar?.visibility = View.GONE
                } catch (e: Exception) {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setCancelable(false)
                    dialog.setTitle(resources.getString(R.string.process_failed))
                    dialog.setMessage(message)
                    dialog.setNegativeButton(no) { DialogInterface, _ ->
                        DialogInterface.dismiss()
                        finish()
                    }
                    dialog.setPositiveButton(yes) { DialogInterface, _ ->
                        loadTahunAjaran(token)
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
                    loadTahunAjaran(token)
                    DialogInterface.dismiss()
                }
                dialog.create().show()
            }
        }
    }

    private fun loadData(token: String, kelasID: Int?, tahunAjaranID: Int?){
        progressbar?.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progressbar, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progressbar.startAnimation(anim)
        val session = SessionManagement(this)
        val title = resources.getString(R.string.connection_failed)
        val message = resources.getString(R.string.connection_try_again)
        val yes = resources.getString(R.string.yes)
        val no = resources.getString(R.string.no)
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.StatusSiswa}/$kelasID/$tahunAjaranID"
        Fuel.get(address).header("token" to token).timeout(10000).responseJson {
            _,response,result ->
            result.success {
                try {
                    val respond = String(response.data)
                    val respondData = Klaxon().parse<Status>(respond)
                    when(respondData?.status) {
                        1 -> {
                            val json = Klaxon().parse<DataAll>(respond)
                            when{
                                json != null -> {
                                    val id = json.siswa.id
                                    val siswa = json.siswa.name
                                    // lanjutkan load data lain
                                    // lanjutkan disini usahakan buat jadi array untuk membuat recycle list

                                }
                            }
                            search_button?.isEnabled = true
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
                    progressbar?.visibility = View.GONE
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
                        loadData(token,kelasID,tahunAjaranID)
                        DialogInterface.dismiss()
                    }
                    dialog.create().show()
                }
            }
            result.failure{
                val dialog = AlertDialog.Builder(this)
                dialog.setCancelable(false)
                dialog.setTitle(title)
                dialog.setMessage(message)
                dialog.setNegativeButton(no) { DialogInterface, _ ->
                    DialogInterface.dismiss()
                    finish()
                }
                dialog.setPositiveButton(yes) { DialogInterface, _ ->
                    loadData(token,kelasID,tahunAjaranID)
                    DialogInterface.dismiss()
                }
                dialog.create().show()
            }
        }
    }
}