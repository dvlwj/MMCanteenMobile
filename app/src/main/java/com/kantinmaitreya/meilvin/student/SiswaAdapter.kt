package com.kantinmaitreya.meilvin.student

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kantinmaitreya.meilvin.R
import com.kantinmaitreya.meilvin.report.ReportActivity
import kotlinx.android.synthetic.main.row_student_list.view.*


class SiswaAdapter (private val dataList: ArrayList<SiswaModel>?) : RecyclerView.Adapter<SiswaAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =  layoutInflater.inflate(R.layout.row_student_list, parent, false)
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
        var row = view.student_row
        var name: TextView = view.student_name
        var nis: TextView = view.student_nis
        private var pagi: TextView = view.student_pagi
        private var siang: TextView = view.student_siang
        fun bind(model: SiswaModel){
            name.text = model.name
            nis.text = itemView.resources.getString(R.string.student_nis,model.nis)
            pagi.text = itemView.resources.getString(R.string.student_pagi,model.pagi)
            siang.text = itemView.resources.getString(R.string.student_siang,model.siang)
            row.setOnClickListener {
                val intent = Intent(itemView.context, ReportActivity::class.java)
                intent.putExtra("NIS", model.nis)
                intent.putExtra("NOHP", model.no_hp)
                itemView.context.startActivity(intent)
            }
        }
    }
}