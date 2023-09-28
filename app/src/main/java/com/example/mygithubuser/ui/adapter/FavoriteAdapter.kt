package com.example.mygithubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.database.local.entity.UserEntity
import com.example.mygithubuser.database.local.helper.NoteDiffCallback
import com.example.mygithubuser.databinding.ItemCardBinding
import com.example.mygithubuser.ui.activity.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorite = ArrayList<UserEntity>()
    fun setListNotes(listNotes: List<UserEntity>) {
        val diffCallback = NoteDiffCallback(this.listFavorite, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    inner class FavoriteViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: UserEntity) {
            Glide.with(binding.root.context)
                .load(favorite.avatarUrl)
                .into(binding.itemImage)
            binding.itemName.text = "${favorite.username}"


            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailActivity::class.java)

                intentDetail.putExtra(DetailActivity.USERNAME, favorite.username)
                intentDetail.putExtra(DetailActivity.USER_ID, favorite.id)
                intentDetail.putExtra(DetailActivity.USER_URL, favorite.id)

                itemView.context.startActivity(intentDetail)
            }
        }
    }
}