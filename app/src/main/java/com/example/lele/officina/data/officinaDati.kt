package com.example.lele.officina.data

class officinaDati( val idName: String,val id: String,val data: String ,val name: String,val telefono: String, val mail: String, val marca: String, val targa: String ,val checkRev: Boolean, val dataRev: String, val checkTagl: Boolean, val dataTagl: String, val note: String){
    constructor() : this ("","","","","","","", "",false , "", false, "", "")
}