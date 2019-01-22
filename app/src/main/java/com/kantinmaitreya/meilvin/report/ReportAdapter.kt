package com.kantinmaitreya.meilvin.report

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kantinmaitreya.meilvin.R
import kotlinx.android.synthetic.main.row_report_list.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ReportAdapter (private val dataList: ArrayList<ReportModel>?) : RecyclerView.Adapter<ReportAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =  layoutInflater.inflate(R.layout.row_report_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        dataList?.let{
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var time: TextView = view.report_date
        var status: TextView = view.report_status
        var harga: TextView = view.report_harga
        fun bind(model: ReportModel){
            val indonesiaDateFormat = Locale("in","ID","ID")
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.US)
            val outputDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy",indonesiaDateFormat)
            val dateParse = inputDateFormat.parse(model.time)
            val dateTimeFinal = outputDateFormat.format(dateParse)
            val currency =  NumberFormat.getNumberInstance(Locale.US).format(model.harga?.toInt())
            time.text = dateTimeFinal
            status.text = model.status?.capitalize()
            harga.text = itemView.resources.getString(R.string.currency,currency)
        }
    }
}