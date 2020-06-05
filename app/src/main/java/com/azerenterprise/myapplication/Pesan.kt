package com.azerenterprise.myapplication

class Pesan(var id:String,
            var fromid:String,
            var toid:String,
            var text:String,
            var time:Long) {
    constructor():this("", "", "", "", -1)
}