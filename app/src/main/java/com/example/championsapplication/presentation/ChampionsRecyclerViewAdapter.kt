package com.example.championsapplication.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.championsapplication.BuildConfig
import com.example.championsapplication.data.api.ApiConstants
import com.example.championsapplication.data.model.Champion
import com.example.championsapplication.databinding.LayoutItemChampionsListBinding

class ChampionsRecyclerViewAdapter(
    private val values: List<Champion>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ChampionsRecyclerViewAdapter.ChampionViewHolder>() {

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
        holder.bindView(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ChampionViewHolder(private val binding: LayoutItemChampionsListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindView(champion: Champion) {
            binding.txtName.text = champion.name
            binding.txtTitle.text = champion.title
            champion.championImage?.let {
                val imageURL =
                    BuildConfig.BASE_URL + ApiConstants.IMAGE_URL + it.full
                Glide.with(binding.root.context).load(imageURL).into(binding.imgProfile)
            }
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(bindingAdapterPosition)
        }
    }

}