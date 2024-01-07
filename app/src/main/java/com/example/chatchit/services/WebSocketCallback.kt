package com.example.chatchit.services

import com.example.chatchit.models.Message

interface WebSocketCallback {
    fun onReceiveMessage(message: Message)
}