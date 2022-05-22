package com.kmandina.testmobile.ui.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kmandina.testmobile.data.api.serialize.TX
import com.kmandina.testmobile.databinding.ItemTrxBinding

class TrxAdapter : ListAdapter<TX, RecyclerView.ViewHolder>(
    TXDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TrxViewHolder(
            ItemTrxBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tx = getItem(position)
        (holder as TrxViewHolder).bind(tx)

    }


    class TrxViewHolder(
        var binding: ItemTrxBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TX) {
            binding.apply {
                trx = item
                executePendingBindings()
            }

        }
    }

}

class TXDiffCallback : DiffUtil.ItemCallback<TX>() {

    fun esIgual(old: TX, new: TX): Boolean {
        return old.cinema == new.cinema
                && old.message == new.message
                && old.date == new.date
                && old.points == new.points

    }

    override fun areItemsTheSame(oldItem: TX, newItem: TX): Boolean {
        return esIgual(oldItem, newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: TX, newItem: TX): Boolean {
        return oldItem == newItem
    }
}