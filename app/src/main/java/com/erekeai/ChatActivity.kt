package com.erekeai

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.erekeai.api.AIClient
import com.erekeai.api.AIRequest
import com.erekeai.api.MessageData
import com.erekeai.databinding.ActivityChatBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView
        adapter = ChatAdapter()
        binding.rvMessages.layoutManager = LinearLayoutManager(this)
        binding.rvMessages.adapter = adapter

        // Кнопка отправки
        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        // Отправка по Enter (Send)
        binding.etMessage.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else false
        }
    }

    private fun sendMessage() {
        val text = binding.etMessage.text.toString().trim()
        if (text.isEmpty()) return

        // Добавляем сообщение пользователя в чат
        adapter.addMessage(Message(text, true, System.currentTimeMillis()))
        binding.etMessage.setText("")
        scrollToBottom()

        // Асинхронный вызов Grok/OpenAI
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = AIClient.api.chat(
                    AIRequest(
                        model = "grok-2-latest",
                        messages = listOf(
                            MessageData("user", text)
                        )
                    )
                )

                val reply = response.choices.first().message.content

                adapter.addMessage(
                    Message(reply, false, System.currentTimeMillis())
                )
            } catch (e: Exception) {
                adapter.addMessage(
                    Message("Ошибка ИИ: ${e.localizedMessage}", false, System.currentTimeMillis())
                )
            }

            scrollToBottom()
        }
    }

    private fun scrollToBottom() {
        binding.rvMessages.post {
            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
        }
    }
}

