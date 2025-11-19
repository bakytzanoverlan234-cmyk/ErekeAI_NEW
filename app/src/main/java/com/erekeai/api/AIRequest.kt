package com.erekeai.api

data class AIRequest(
    val model: String,
    val messages: List<MessageData>
)

data class MessageData(
    val role: String,
    val content: String
)
