package com.erekeai

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val messages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_USER = 0
    private val TYPE_AI = 1
    private val TYPE_IMAGE = 2

    override fun getItemViewType(position: Int): Int {
        val msg = messages[position]
        return when {
            msg.isImage -> TYPE_IMAGE
            msg.isUser -> TYPE_USER
            else -> TYPE_AI
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            TYPE_USER -> UserHolder(inflater.inflate(R.layout.msg_user, parent, false))
            TYPE_AI -> AiHolder(inflater.inflate(R.layout.msg_ai, parent, false))
            TYPE_IMAGE -> ImageHolder(inflater.inflate(R.layout.item_image_message, parent, false))
            else -> throw IllegalArgumentException("bad type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        val msg = messages[pos]
        when (holder) {
            is UserHolder -> holder.text.text = msg.text
            is AiHolder -> holder.text.text = msg.text
            is ImageHolder -> holder.image.setImageBitmap(msg.imageBitmap)
        }
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(msg: ChatMessage) {
        messages.add(msg)
        notifyItemInserted(messages.size - 1)
    }

    class UserHolder(v: View) : RecyclerView.ViewHolder(v) {
        val text: TextView = v.findViewById(R.id.userMessage)
    }

    class AiHolder(v: View) : RecyclerView.ViewHolder(v) {
        val text: TextView = v.findViewById(R.id.aiMessage)
    }

    class ImageHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.findViewById(R.id.imageMessage)
    }
}
