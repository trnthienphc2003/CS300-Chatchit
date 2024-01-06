package com.example.chatchit.component.data

import com.example.chatchit.models.Sender

data class Chat(
    val id:Int,
//    val lastMes:Boolean = true,
    val content:String,
    val roomId:Int,
    val senderid:Int,
    val user: Sender,
    val metadata:String ?= "test",
    val lastMes: Boolean
//    val isUser:Boolean
)

val chatList = listOf(
    Chat(
        1,
        "Hey! How have you been?",
        4,
        3,
        Sender(0),
        lastMes = false
    ),
    Chat(
        2,
        "Wanna catch up for a beer?",
        4,
        3,
        Sender(),
        lastMes = true
    ),
    Chat(
        3,
        "Awesome! Let’s meet up",
        4,
        1,
        Sender(),
        lastMes = false
    ),
    Chat(
        4,
        "Can I also get my cousin along? Will that be okay?",
        4,
        1,
        Sender(),
        lastMes = true
    ),
//    Chat(
//        5,
//        true,
//        "Yeah sure! get him too.",
//        "12:21 PM",
//        true
//    ),
//    Chat(
//        6,
//        true,
//        "Hey! How have you been?",
//        "12:15 PM",
//        false
//    ),
//    Chat(
//        7,
//        true,
//        "Wanna catch up for a beer?",
//        "12:17 PM",
//        true
//    ),
//    Chat(
//        8,
//        false,
//        "Awesome! Let’s meet up",
//        "12:19 PM",
//        false
//    ),
//    Chat(
//        9,
//        true,
//        "Can I also get my cousin along? Will that be okay?",
//        "12:20 PM",
//        false
//    ),
//    Chat(
//        10,
//        false,
//        "Yeah sure! get him too.",
//        "12:21 PM",
//        true
//    ),
//    Chat(
//        11,
//        true,
//        "Yeah sure! get him too.",
//        "12:21 PM",
//        true
//    ),
)