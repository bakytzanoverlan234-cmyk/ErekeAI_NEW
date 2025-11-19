package com.erekeai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<Message>()

    companion object {
        private const val TYPE_USER = 1
        private const val TYPE_AI = 2
        private const val TYPE_IMAGE = 3
    }

    fun addMessage(msg: Message) {
        items.add(msg)
        notifyItemInserted(items.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        val m = items[position]
        return when {
            m.imageUrl != null -> TYPE_IMAGE
            m.isUser -> TYPE_USER
            else -> TYPE_AI
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_USER -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_message, parent, false)
                UserVH(v)
            }
            TYPE_AI -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ai_message, parent, false)
                AiVH(v)
            }
            else -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_message, parent, false)
                ImageVH(v)
            }
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = items[position]

        when(holder) {
            is UserVH -> holder.text.text = msg.text
            is AiVH -> holder.text.text = msg.text
            is ImageVH -> {
                holder.image.load(msg.imageUrl)
                holder.text.text = msg.text
            }
        }
    }

    class UserVH(v: View) : RecyclerView.ViewHolder(v) {
        val text: TextView = v.findViewById(R.id.textMessage)
    }

    class AiVH(v: View) : RecyclerView.ViewHolder(v) {
        val text: TextView = v.findViewById(R.id.textMessage)
    }

    class ImageVH(v: View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.findViewById(R.id.imageMessage)
        val text: TextView = v.findViewById(R.id.textMessage)
    }
}
