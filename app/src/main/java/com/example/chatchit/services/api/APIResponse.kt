package com.example.chatchit.services.api

data class APIResponse(
    var success: String,
    var message: String,
    var statusCode: Int,
    var data: Any? = null,
)
