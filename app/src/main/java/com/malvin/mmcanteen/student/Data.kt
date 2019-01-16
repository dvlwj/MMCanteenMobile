package com.malvin.mmcanteen.student

class Status(val status: Int)

class DataKelas(val status: Int, val kelas: Kelas)

class Kelas(val id: Int, val name: String)

class DataTahunAjaran(val status: Int, val th_ajaran: TahunAjaran)

class TahunAjaran(val id: Int, val tahun: Int)

class DataAll(val siswa: Siswa)

class Siswa(val id: Int, val nis: Int, val name: String, val kelas_id: Int, val th_ajaran_id: Int)

//class DataKelas(val status: Int, val kelas: List<Status>)