package com.example.chatchit.component.data

data class Chat(
    val id:Int,
    val lastMes:Boolean,
    val message:String,
    val time:String,
    val isUser:Boolean
)

val chatList = listOf(
    Chat(
        1,
        false,
        "Hey! How have you been?",
        "12:15 PM",
        true
    ),
    Chat(
        2,
        true,
        "Wanna catch up for a beer?",
        "12:17 PM",
        true
    ),
    Chat(
        3,
        false,
        "Awesome! Let’s meet up",
        "12:19 PM",
        false
    ),
    Chat(
        4,
        true,
        "Can I also get my cousin along? Will that be okay?",
        "12:20 PM",
        false
    ),
    Chat(
        5,
        true,
        "Yeah sure! get him too.",
        "12:21 PM",
        true
    ),
    Chat(
        6,
        true,
        "Hey! How have you been?",
        "12:15 PM",
        false
    ),
    Chat(
        7,
        true,
        "Wanna catch up for a beer?",
        "12:17 PM",
        true
    ),
    Chat(
        8,
        false,
        "Awesome! Let’s meet up",
        "12:19 PM",
        false
    ),
    Chat(
        9,
        true,
        "Can I also get my cousin along? Will that be okay?",
        "12:20 PM",
        false
    ),
    Chat(
        10,
        false,
        "Yeah sure! get him too.",
        "12:21 PM",
        true
    ),
    Chat(
        11,
        true,
        "Yeah sure! get him too.",
        "12:21 PM",
        true
    ),
)