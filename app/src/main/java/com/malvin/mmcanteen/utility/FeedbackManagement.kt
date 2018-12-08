package com.malvin.mmcanteen.utility

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

typealias  funUnit = () -> Unit

class FeedbackManagement(val context: Context) {

    fun showToastLong(message: String?){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    fun showToastShort(message: String?){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun showAlertDialog(title: String?, message: String?, cancelable: Boolean?, neutralButtonText: String?, positiveButtonText: String?, negativeButtonText: String?, functionPositive: funUnit, functionNegative: funUnit){
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        dialog.setMessage(message)
        when{
            neutralButtonText != "none" -> {
                dialog.setNeutralButton(neutralButtonText){
                    DialogInterface, _ ->
                    DialogInterface.dismiss()
                }
            }
        }
        when{
            positiveButtonText != "none" -> {
                dialog.setPositiveButton(positiveButtonText){
                    DialogInterface, _ ->
                    functionPositive.invoke()
                    DialogInterface.dismiss()
                }
            }
        }
        when{
            negativeButtonText != "none" -> {
                dialog.setPositiveButton(negativeButtonText){
                    DialogInterface, _ ->
                    functionNegative.invoke()
                    DialogInterface.dismiss()
                }
            }
        }
        when(cancelable){
            true  -> dialog.setCancelable(true)
            false -> dialog.setCancelable(false)
        }
        dialog.create().show()
    }
}