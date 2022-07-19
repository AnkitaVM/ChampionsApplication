package com.example.championsapplication.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.championsapplication.BuildConfig
import com.example.championsapplication.data.api.ApiConstants
import com.example.championsapplication.databinding.LayoutItemChampionsListBinding
import com.example.championsapplication.domain.model.Champion
import javax.inject.Inject

class ChampionsRecyclerViewAdapter @Inject constructor(
) : RecyclerView.Adapter<ChampionsRecyclerViewAdapter.ChampionViewHolder>() {

    private lateinit var onChampionClickListener: ((String) -> Unit)

    private val callback = object : DiffUtil.ItemCallback<Champion>() {
        override fun areItemsTheSame(oldItem: Champion, newItem: Champion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Champion, newItem: Champion): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampionViewHolder {

        return ChampionViewHolder(
            LayoutItemChampionsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ChampionViewHolder, position: Int) {
        holder.bindView(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ChampionViewHolder(private val binding: LayoutItemChampionsListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(champion: Champion) {
            binding.txtName.text = champion.name
            binding.txtTitle.text = champion.title
            champion.championImage?.let {
                val imageURL =
                    BuildConfig.BASE_URL + ApiConstants.IMAGE_URL + it.full
                Glide.with(binding.root.context).load(imageURL).into(binding.imgProfile)
            }

            binding.root.setOnClickListener { onChampionClickListener(champion.id) }
        }
    }

    fun setChampionItemClickListener(listener: ((String) -> Unit)) {
        onChampionClickListener = listener
    }

}