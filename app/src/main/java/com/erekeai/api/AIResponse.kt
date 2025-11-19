package com.erekeai.api

data class AIResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: AIMessage
)

data class AIMessage(
    val role: String,
    val content: String
)
