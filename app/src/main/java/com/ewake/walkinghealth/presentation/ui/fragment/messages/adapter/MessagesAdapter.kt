package com.ewake.walkinghealth.presentation.ui.fragment.messages.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewake.walkinghealth.databinding.MessageItemBinding
import com.ewake.walkinghealth.presentation.model.MessageModel

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    var items = mutableListOf<MessageModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MessageViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MessageModel) {
            binding.apply {
                message.text = item.message
                time.text = item.timestamp.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MessageViewHolder(MessageItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}