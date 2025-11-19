package com.erekeai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.erekeai.api.ApiClient
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private val adapter = ChatAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatRecycler.layoutManager = LinearLayoutManager(this)
        chatRecycler.adapter = adapter

        sendBtn.setOnClickListener {
            val text = messageInput.text.toString()
            if (text.isNotEmpty()) {
                adapter.addMessage(ChatMessage(text = text, isUser = true))
                messageInput.setText("")

                ApiClient().sendMessage(text, object : ApiClient.Callback {
                    override fun onSuccess(text: String) {
                        runOnUiThread {
                            adapter.addMessage(ChatMessage(text = text, isUser = false))
                        }
                    }

                    override fun onError(error: String) {
                        runOnUiThread {
                            adapter.addMessage(ChatMessage(text = "Error: $error"))
                        }
                    }
                })
            }
        }
    }
}
