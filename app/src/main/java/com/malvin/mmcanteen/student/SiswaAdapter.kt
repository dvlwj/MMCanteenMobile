package com.malvin.mmcanteen.student

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.malvin.mmcanteen.R
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

//    private var siswaList: MutableList<SiswaModel>? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.student_name
        var nis: TextView = view.student_nis
        private var pagi: TextView = view.student_pagi
        private var siang: TextView = view.student_siang
//        var name: TextView = view.findViewById(R.id.student_name) as TextView
//        var nis: TextView = view.findViewById(R.id.student_nis) as TextView
//        var pagi: TextView = view.findViewById(R.id.student_pagi) as TextView
//        var siang: TextView = view.findViewById(R.id.student_siang) as TextView
        fun bind(model: SiswaModel){
            name.text = model.name
            nis.text = model.nis
            pagi.text = model.pagi
            siang.text = model.siang
        }
    }

//    fun SiswaAdapter(siswaList: MutableList<SiswaModel>){
//        this.siswaList = siswaList
//    }



//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val movie = siswaList?.get(position)
//        holder.name.text = movie?.getName()
//        holder.nis.text = movie?.getNis()
//        holder.pagi.text = movie?.getPagi()
//        holder.siang.text = movie?.getSiang()
//    }
//
//    override fun getItemCount(): Int {
//        return siswaList?.size ?: 0
////        return data.size
//    }
}