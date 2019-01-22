package com.malvin.mmcanteen.register

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.malvin.mmcanteen.R
import com.malvin.mmcanteen.utility.FeedbackManagement
import com.malvin.mmcanteen.utility.ProgressBarAnimation
import com.malvin.mmcanteen.utility.ServerAddress
import com.malvin.mmcanteen.utility.SessionManagement
import kotlinx.android.synthetic.main.activity_register_student.*


class StudentRegisterActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_register_student)
        button_confirm?.isEnabled = false
        val session = SessionManagement(this)
        val token = session.checkData(session.keyToken).toString()
        loadKelas(token)
        button_confirm?.setOnClickListener {
            checkForm()
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
        val spinnerKelas = register_kelas
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
                                    val data = ArrayList(json.kelas)
                                    data.toArray()
                                    val spinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
                                    spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    spinnerKelas?.adapter = spinner
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
        val spinnerTahun = register_tahun
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
                                    val data = ArrayList(json.th_ajaran)
                                    data.toArray()
                                    val spinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
                                    spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    spinnerTahun?.adapter = spinner
                                    button_confirm?.isEnabled = true
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

    private fun checkForm(){
        val kelasID = (register_kelas?.selectedItem as Kelas).id
        val tahunAjaranID = (register_tahun?.selectedItem as TahunAjaran).id
        val nis = register_nis?.text
        val nama = register_name?.text
        val nohp = register_nohp?.text
        val pagi = register_pagi?.isChecked
        val siang = register_siang?.isChecked
        val aktif = "aktif"
        val nonaktif = "non aktif"
        val fbM = FeedbackManagement(this)
        when{
            nis.isNullOrEmpty() -> {
                fbM.showToastLong(resources.getString(R.string.register_nis_empty))
                register_nis?.requestFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(register_nis, InputMethodManager.SHOW_IMPLICIT)
            }
            nama.isNullOrEmpty() -> {
                fbM.showToastLong(resources.getString(R.string.register_name_empty))
                register_name?.requestFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(register_name, InputMethodManager.SHOW_IMPLICIT)
            }
            nohp.isNullOrEmpty() -> {
                fbM.showToastLong(resources.getString(R.string.register_nohp_empty))
                register_nohp?.requestFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(register_nohp, InputMethodManager.SHOW_IMPLICIT)
            }
            kelasID == 0 -> {
                fbM.showToastLong(resources.getString(R.string.register_kelas_empty))
                register_kelas?.requestFocus()
                register_kelas?.performClick()
            }
            tahunAjaranID == 0 -> {
                fbM.showToastLong(resources.getString(R.string.register_tahun_empty))
                register_tahun?.requestFocus()
                register_tahun?.performClick()
            }
            pagi == false && siang == false -> {
                fbM.showToastLong(resources.getString(R.string.register_status_empty))
                register_pagi?.requestFocus()
            }
            else -> {
                when {
                    pagi == true && siang == false -> sendForm(kelasID,tahunAjaranID,nis.toString(),nama.toString(),nohp.toString(),aktif,nonaktif)
                    pagi == false && siang == true -> sendForm(kelasID,tahunAjaranID,nis.toString(),nama.toString(),nohp.toString(),nonaktif,aktif)
                    pagi == true && siang == true -> sendForm(kelasID,tahunAjaranID,nis.toString(),nama.toString(),nohp.toString(),aktif,aktif)
                }
            }
        }
    }

    private fun sendForm(kelas: Int,tahunAjaran: Int,nis: String, nama: String, nohp: String, pagi: String, siang: String){
        button_confirm?.isEnabled = false
        progressbar?.visibility = View.VISIBLE
        val anim = ProgressBarAnimation(progressbar, 0.toFloat(), 100.toFloat())
        anim.duration = 1000
        progressbar.startAnimation(anim)
        val title = resources.getString(R.string.connection_failed)
        val message = resources.getString(R.string.connection_try_again)
        val yes = resources.getString(R.string.yes)
        val no = resources.getString(R.string.no)
        val fbM = FeedbackManagement(this)
        val session = SessionManagement(this)
        val token = session.checkData(session.keyToken).toString()
        val address = "${ServerAddress.http}${session.checkServerAddress(session.keyServerAddress)}${ServerAddress.StatusSiswa}"
        val dataServer = listOf("nis" to nis, "name" to nama, "no_hp" to nohp, "kelas_id" to kelas, "th_ajaran_id" to tahunAjaran, "pagi" to pagi, "siang" to siang)
        Fuel.post(address, dataServer).header("token" to token).timeout(10000).responseJson { _, response, result ->
            result.success {
                try {
                    val respond = String(response.data)
                    val siswa = Klaxon().parse<Status>(respond)
                    when(siswa?.status) {
                        0 -> {
                            val dialog = AlertDialog.Builder(this)
                            dialog.setCancelable(false)
                            dialog.setTitle(resources.getString(R.string.error))
                            dialog.setMessage(resources.getString(R.string.register_nis_exist))
                            dialog.setNeutralButton(yes) { DialogInterface, _ ->
                                DialogInterface.dismiss()
                                register_nis?.requestFocus()
                                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.showSoftInput(register_nis, InputMethodManager.SHOW_IMPLICIT)
                            }
                            dialog.create().show()
                        }
                        1 -> {
                            fbM.showToastLong(resources.getString(R.string.register_success,nama,nis))
                            register_nis?.text?.clear()
                            register_name?.text?.clear()
                            register_nohp?.text?.clear()
                            register_pagi?.isChecked = false
                            register_siang?.isChecked = false
                            register_nis?.requestFocus()
                            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.showSoftInput(register_nis, InputMethodManager.SHOW_IMPLICIT)
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
                    button_confirm?.isEnabled = true
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
                        sendForm(kelas, tahunAjaran, nis, nama, nohp, pagi, siang)
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
                    sendForm(kelas, tahunAjaran, nis, nama, nohp, pagi, siang)
                    DialogInterface.dismiss()
                }
                dialog.create().show()
            }
        }
    }
}