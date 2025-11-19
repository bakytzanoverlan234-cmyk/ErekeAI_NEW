package com.erekeai.api

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object ApiClient {

    private const val BASE_URL = "https://api.groq.com/openai/v1/chat/completions"
    private const val API_KEY = "gsk_cor9eqqpF98TGjZSdVboWGd"

    private val client = OkHttpClient()

    interface Callback {
        fun onSuccess(text: String)
        fun onError(error: String)
    }

    fun ask(prompt: String, callback: Callback) {
        val json = JSONObject()
        json.put("model", "llama3-8b-8192")
        json.put("temperature", 0.6)

        val messages = JSONObject()
        messages.put("role", "user")
        messages.put("content", prompt)

        json.put("messages", listOf(messages))

        val body = RequestBody.create(
            MediaType.parse("application/json"), 
            json.toString()
        )

        val request = Request.Builder()
            .url(BASE_URL)
            .header("Authorization", "Bearer $API_KEY")
            .post(body)
            .build()

        client.newCall(request).enqueue(object: Callback, okhttp3.Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val text = JSONObject(response.body()?.string() ?: "")
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")

                    callback.onSuccess(text)
                } catch (e: Exception) {
                    callback.onError(e.message ?: "Parsing error")
                }
            }
        })
    }
}
