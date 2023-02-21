package com.example.calcapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calcapp.databinding.ItemHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private lateinit var binding: ItemHistoryBinding

    var onItemClick: ((CalcResult) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding=ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun getItemCount() = differ.currentList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)

    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
      fun setData(item:CalcResult){
          binding.apply {
              tvId.text=item.operation
              tvResult.text=item.result
              itemViewOnClick.setOnClickListener {
                  onItemClick?.invoke(item)
              }
          }
      }
    }

    private val differCallback = object : DiffUtil.ItemCallback<CalcResult>(){
        override fun areItemsTheSame(oldItem: CalcResult, newItem: CalcResult): Boolean {
           return oldItem.operation==newItem.operation
        }

        override fun areContentsTheSame(oldItem: CalcResult, newItem: CalcResult): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,differCallback)

}