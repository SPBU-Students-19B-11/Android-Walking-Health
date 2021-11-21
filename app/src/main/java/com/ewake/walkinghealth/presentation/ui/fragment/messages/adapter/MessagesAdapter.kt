package com.ewake.walkinghealth.presentation.ui.fragment.messages.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewake.walkinghealth.databinding.MessageItemBinding
import com.ewake.walkinghealth.presentation.model.MessageModel
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

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
                time.text = SimpleDateFormat("dd.MM.yyy HH:mm", Locale.getDefault()).format(Date(item.timestamp * 1000))
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