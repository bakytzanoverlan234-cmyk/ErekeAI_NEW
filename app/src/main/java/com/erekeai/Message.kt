package com.erekeai

data class Message(
    val text: String?,
    val isUser: Boolean,
    val timestamp: Long,
    val imageUrl: String? = null
)
