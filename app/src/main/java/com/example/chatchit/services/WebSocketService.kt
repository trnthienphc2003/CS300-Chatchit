package com.example.chatchit.services

import android.content.Context
import android.util.Log
import com.example.chatchit.models.Message
import com.example.chatchit.models.MessageTranslate
import com.example.chatchit.services.api.form.MessageForm
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener


class WebSocketService private constructor() {
    private val BASE_URL = "http://103.82.39.199:8000/api/v1/client/ws/"
    private var webSocket: WebSocket? = null
    private var callback: WebSocketCallback? = null
    private var success = false

    fun setup(context: Context, userId: Int) {
        if (webSocket != null) {
            closeConnection()
        }
        val uri : String = BASE_URL + userId.toString()
        val requestBuilder = Request.Builder()
            .get()
            .url(uri)
        for (cookie in APIService.getCookies(context)!!) {
            requestBuilder.addHeader("Cookie", cookie)
        }

        val request = requestBuilder.build()
        webSocket = OkHttpClient().newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: okhttp3.WebSocket, response: okhttp3.Response) {
                Log.i("WebSocket", "Connected to server")
                webSocket.send("ping")
                Log.i("WebSocket", "Send data: ping")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                success = true
                Log.i("WebSocket", "Received data:$text")
                if (text.trim() == "\"pong\"")
                    return
                receiveMessage(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, reason)
                Log.i("WebSocket", "Closing websocket $reason")
            }

            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: okhttp3.Response?
            ) {
                Log.i("WebSocket", "Error: " + t.message)
                throw t
            }
        })

        webSocket?.send("ping")
    }

    fun setCallback(callback: WebSocketCallback) {
        this.callback = callback
    }

    fun isConnected(): Boolean {
        return success
    }

    private fun receiveMessage(message: String){
        Log.i("WebSocket", "hmu: $message \"ping\"")
        callback?.onReceiveMessage(Gson().fromJson(message, MessageTranslate::class.java))
    }

    fun closeConnection() {
        if (webSocket == null)
            return
        Log.i("WebSocket", "Close connection")
        webSocket?.close(1000, "Close connection")
        webSocket = null
        success = false
    }

    fun sendMessage(messageForm: MessageForm) {
        if (webSocket != null) {
            webSocket?.send(Gson().toJson(messageForm))
            Log.i("WebSocket", "Send data: ${Gson().toJson(messageForm)}")
        }
    }

    companion object {
        private val instance: WebSocketService = WebSocketService()

        fun getInstance(): WebSocketService {
            return instance
        }
    }
}