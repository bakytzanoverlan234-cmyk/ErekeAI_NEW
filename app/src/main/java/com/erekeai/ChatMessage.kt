package com.erekeai

import android.graphics.Bitmap

data class ChatMessage(
    val text: String? = null,
    val isUser: Boolean = false,
    val isImage: Boolean = false,
    val imageBitmap: Bitmap? = null
)
