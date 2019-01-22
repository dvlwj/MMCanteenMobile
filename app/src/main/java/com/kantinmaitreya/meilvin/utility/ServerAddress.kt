package com.kantinmaitreya.meilvin.utility

object ServerAddress {
    const val AppVersion = "1.0"
    const val localhost = "127.0.0.1"
    const val http = "http://"
    private const val MainUrl = "/api/v1/"
    const val Login = MainUrl+"user/signin"
    const val Petugas = MainUrl+"petugas"
    const val Absen = MainUrl+"absen"
    const val StatusSiswa = MainUrl+"siswa/"
    const val Kelas = MainUrl+"siswa/kelas"
    const val TahunAjaran = MainUrl+"siswa/th-ajaran"
    const val LaporanMakan = MainUrl+"report"
}