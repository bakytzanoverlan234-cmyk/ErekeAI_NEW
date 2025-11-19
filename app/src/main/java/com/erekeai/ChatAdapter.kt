package com.erekeai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_USER = 0
        private const val TYPE_AI = 1
        private const val TYPE_IMAGE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            messages[position].isImage -> TYPE_IMAGE
            messages[position].isUser -> TYPE_USER
            else -> TYPE_AI
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_USER -> UserHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.msg_user, parent, false)
            )

            TYPE_AI -> AiHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.msg_ai, parent, false)
            )

            TYPE_IMAGE -> ImageHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_message, parent, false)
            )

            else -> throw IllegalArgumentException("invalid")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]
        when (holder) {
            is UserHolder -> holder.text.text = msg.text
            is AiHolder -> holder.text.text = msg.text
            is ImageHolder -> holder.image.setImageBitmap(msg.imageBitmap)
        }
    }

    override fun getItemCount(): Int = messages.size

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
