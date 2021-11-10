package com.ewake.walkinghealth.presentation.ui.fragment.profiledoctor.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewake.walkinghealth.databinding.ItemPatientBinding
import com.ewake.walkinghealth.presentation.model.SimpleUserModel

class PatientsAdapter: RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>() {

    inner class PatientViewHolder(private val binding: ItemPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.profile.setOnClickListener {
                onItemClickListener?.invoke(items[adapterPosition])
            }
        }

        fun bind(model: SimpleUserModel) {
            binding.profile.model = model
        }
    }

    var items: List<SimpleUserModel> = listOf()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onItemClickListener: ((SimpleUserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PatientViewHolder(ItemPatientBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}