package com.ewake.walkinghealth.presentation.ui.fragment.profilepatient.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewake.walkinghealth.data.local.room.entity.UserActivityData
import com.ewake.walkinghealth.databinding.ItemUserActivityBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityDataAdapter : RecyclerView.Adapter<ActivityDataAdapter.ViewHolder>() {

    var items: List<UserActivityData> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: ItemUserActivityBinding) : RecyclerView.ViewHolder(binding.root) {

        private val formatter = "%.2f"

        @SuppressLint("SetTextI18n")
        fun bind(item: UserActivityData) {
            binding.apply {
                time.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(item.timestamp))
                distance.text = formatter.format(item.distance) + " м"
                speed.text = formatter.format(item.speed) + " км/ч"
                acceleration.text = formatter.format(item.acceleration) + " м/с²"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserActivityBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}