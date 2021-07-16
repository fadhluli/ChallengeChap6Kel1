package com.fadtech.challengechap6kel1.ui.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fadtech.challengechap6kel1.data.model.User
import com.fadtech.challengechap6kel1.databinding.RankingListBinding


class RankingAdapter: RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    var items: List<User> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class RankingViewHolder(
        private val binding: RankingListBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bindView(item: User, position: Int) {
            with(item) {
                binding.tvUsername.text = username
                binding.tvTotalWin.text = totalWin.toString()
                binding.tvRanking.text = (position + 1).toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding = RankingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bindView(items[position], position)
    }

    override fun getItemCount(): Int = items.size


}