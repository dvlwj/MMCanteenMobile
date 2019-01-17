package com.malvin.mmcanteen.student

class SiswaModel(
    var id: Int?,
    var nis: String?,
    var name: String?,
    var kelas_id: Int?,
    var th_ajaran_id: Int?,
    var pagi: String?,
    var siang: String?
)

//class SiswaModel {
//    private var nis: String? = null
//    private var name: String? = null
//    private var pagi: String? = null
//    private var siang: String? = null
//
//    fun siswa(){}
//
//    fun siswa(nis: String, name: String, pagi: String, siang: String){
//        this.nis = nis
//        this.name = name
//        this.pagi = pagi
//        this.siang= siang
//    }
//
//    fun getNis(): String? {
//        return nis
//    }
//
//    fun setNis(nis: String) {
//        this.nis = nis
//    }
//
//    fun getName(): String? {
//        return name
//    }
//
//    fun setName(name: String) {
//        this.name = name
//    }
//
//    fun getPagi(): String? {
//        return pagi
//    }
//
//    fun setPagi(pagi: String){
//        this.pagi = pagi
//    }
//
//    fun getSiang(): String? {
//        return siang
//    }
//
//    fun setSiang(siang: String){
//        this.siang = siang
//    }
//}