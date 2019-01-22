package com.malvin.mmcanteen.register

class Status(val status: Int)

class DataKelas(val status: Int, val kelas: List<Kelas>)

class Kelas(val id: Int, val name: String){
    override fun toString(): String {return name}
}

class DataTahunAjaran(val status: Int, val th_ajaran: List<TahunAjaran>)

class TahunAjaran(val id: Int, val tahun: String){
    override fun toString(): String {return tahun}
}