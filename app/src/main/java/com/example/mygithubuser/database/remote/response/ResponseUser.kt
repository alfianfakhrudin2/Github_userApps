package com.example.mygithubuser.database.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(

    @field:SerializedName("total_count") val totalCount: Int,

    @field:SerializedName("incomplete_results") val incompleteResults: Boolean,

    @field:SerializedName("items") val items: List<ItemsItem>
)

data class ItemsItem(

    @field:SerializedName("following_url") val followingUrl: String,

    @field:SerializedName("login") val login: String,
    
    @field:SerializedName("followers_url") val followersUrl: String,

    @field:SerializedName("url") val url: String,

    @field:SerializedName("avatar_url") val avatarUrl: String,

    @field:SerializedName("id") val id: Int,
)
