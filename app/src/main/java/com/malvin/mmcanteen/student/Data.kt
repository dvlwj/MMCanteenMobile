package com.malvin.mmcanteen.student

class Status(val status: Int)

class DataKelas(val status: Int, val kelas: List<Kelas>)

class Kelas(val id: Int, val name: String){
    override fun toString(): String {return name}
}

class DataTahunAjaran(val status: Int, val th_ajaran: List<TahunAjaran>)

class TahunAjaran(val id: Int, val tahun: String){
    override fun toString(): String {return tahun}
}

class DataAll(val siswa: List<Siswa>)

class Siswa(val id: Int, val nis: String, val name: String, val kelas_id: Int, val th_ajaran_id: Int, val pagi: String, val siang: String)

//class DataKelas(val status: Int, val kelas: List<Status>)