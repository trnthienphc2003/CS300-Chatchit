package com.example.chatchit.services

import com.example.chatchit.models.MessageTranslate

interface WebSocketCallback {
    fun onReceiveMessage(message: MessageTranslate)
}