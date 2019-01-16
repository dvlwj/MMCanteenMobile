package com.malvin.mmcanteen.scan

class ScanStatus(val status: Int)

class Data(val status: Int, val absen: Absen)

class Absen(val user_id: Int, val username: String, val role: String)

class DataActive(val status: Int, val siswa: Siswa)

class Siswa(val pagi: String, val siang: String)