package com.erekeai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.erekeai.api.ApiClient
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
                adapter.addMessage(ChatMessage(text, true))
                binding.messageInput.setText("")

                ApiClient.sendMessage(text, object : ApiClient.Callback {
                    override fun onSuccess(response: String) {
                        runOnUiThread {
                            adapter.addMessage(ChatMessage(response, false))
                        }
                    }

                    override fun onError(error: String) {
                        runOnUiThread {
                            adapter.addMessage(ChatMessage("Ошибка: $error", false))
                        }
                    }
                })
            }
        }
    }
}
