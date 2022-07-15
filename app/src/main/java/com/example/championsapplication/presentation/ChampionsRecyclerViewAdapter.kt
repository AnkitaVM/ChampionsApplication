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
) : RecyclerView.Adapter<ChampionsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutItemChampionsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.txtName.text = item.name
        holder.txtChampionTitle.text = item.title
        if (item.championImage != null) {
            val imageURL =
                BuildConfig.BASE_URL + ApiConstants.IMAGE_URL + item.championImage.full
            Glide.with(holder.itemView.context).load(imageURL).into(holder.imgProfile)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: LayoutItemChampionsListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val txtName: TextView = binding.txtName
        val txtChampionTitle: TextView = binding.txtTitle
        val imgProfile: ImageView = binding.imgProfile

        init {
            binding.root.setOnClickListener(this)
        }

        override fun toString(): String {
            return super.toString() + " '" + txtChampionTitle.text + "'"
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(bindingAdapterPosition)
        }
    }

}