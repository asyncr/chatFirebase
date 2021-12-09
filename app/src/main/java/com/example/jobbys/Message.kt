package com.example.jobbys

class Message {
    var menssage: String? = null
    var senderId: String? = null

    constructor(){}

    constructor(message: String?, senderId:String?){
        this.menssage = message
        this.senderId = senderId
    }
}