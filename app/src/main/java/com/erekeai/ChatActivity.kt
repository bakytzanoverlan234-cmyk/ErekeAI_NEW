package com.erekeai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.erekeai.api.ApiClient
import com.erekeai.api.ApiClient.Callback
import com.erekeai.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val adapter = ChatAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatRecycler.layoutManager = LinearLayoutManager(this)
        binding.chatRecycler.adapter = adapter

        binding.sendBtn.setOnClickListener {
            val text = binding.messageInput.text.toString().trim()
            if (text.isNotEmpty()) {
                addMessage(text, true)
                sendMessage(text)
                binding.messageInput.setText("")
            }
        }
    }

    private fun addMessage(text: String, isUser: Boolean) {
        adapter.addMessage(ChatMessage(text, isUser))
        binding.chatRecycler.scrollToPosition(adapter.itemCount - 1)
    }

    private fun sendMessage(text: String) {
        ApiClient.sendText(text, object : Callback {
            override fun onSuccess(reply: String) {
                addMessage(reply, false)
            }

            override fun onError(error: String) {
                addMessage("Ошибка: $error", false)
            }
        })
    }
}
