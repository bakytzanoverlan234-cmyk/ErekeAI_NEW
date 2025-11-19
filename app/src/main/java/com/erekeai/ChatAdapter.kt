package com.erekeai

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erekeai.databinding.MsgAiBinding
import com.erekeai.databinding.MsgUserBinding

class ChatAdapter(
    private val messages: MutableList<ChatMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_USER = 1
        private const val TYPE_AI = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) TYPE_USER else TYPE_AI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_USER) {
            val binding = MsgUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            UserHolder(binding)
        } else {
            val binding = MsgAiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            AiHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]

        if (holder is UserHolder) holder.binding.userMessage.text = msg.text
        if (holder is AiHolder) holder.binding.aiMessage.text = msg.text
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(msg: ChatMessage) {
        messages.add(msg)
        notifyItemInserted(messages.size - 1)
    }

    class UserHolder(val binding: MsgUserBinding) : RecyclerView.ViewHolder(binding.root)
    class AiHolder(val binding: MsgAiBinding) : RecyclerView.ViewHolder(binding.root)
}
