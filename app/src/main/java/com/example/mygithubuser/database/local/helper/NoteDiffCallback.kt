package com.example.mygithubuser.database.local.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mygithubuser.database.local.entity.UserEntity

class NoteDiffCallback(
    private val oldFavList: List<UserEntity>,
    private val newFavList: List<UserEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newFavList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].id == newFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldFavList[oldItemPosition]
        val newNote = newFavList[newItemPosition]
        return oldNote.username == newNote.username && oldNote.avatarUrl == newNote.avatarUrl
    }
}