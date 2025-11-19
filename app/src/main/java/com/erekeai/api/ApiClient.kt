package com.erekeai.api

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ApiClient {

    private val client = OkHttpClient()

    interface Callback {
        fun onSuccess(text: String)
        fun onError(error: String)
    }

    fun sendMessage(prompt: String, callback: Callback) {
        val json = JSONObject().apply {
            put("prompt", prompt)
        }

        val body = json.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://api.example.com/chat")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback, okhttp3.Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response.body?.string() ?: "")
            }
        })
    }
}
