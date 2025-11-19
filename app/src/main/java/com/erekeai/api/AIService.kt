package com.erekeai.api

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AIService {

    @Headers(
        "Content-Type: application/json"
    )
    @POST("v1/chat/completions")
    suspend fun chat(@Body request: AIRequest): AIResponse
}
