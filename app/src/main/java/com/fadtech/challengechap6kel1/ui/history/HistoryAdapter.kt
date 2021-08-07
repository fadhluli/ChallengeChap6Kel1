package com.fadtech.challengechap6kel1.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fadtech.challengechap6kel1.data.network.entity.responses.History.GetHistoryData
import com.fadtech.challengechap6kel1.databinding.HistoryListBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var items: List<GetHistoryData> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class HistoryViewHolder(
        private val binding: HistoryListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: GetHistoryData, position: Int) {
            with(item) {
                binding.tvResult.text = result
                binding.tvMode.text = mode
                binding.tvMessageResult.text = message
                binding.tvTime.text = createdAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bindView(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}