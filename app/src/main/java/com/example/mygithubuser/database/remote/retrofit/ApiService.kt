package com.example.restaurantreview.data.retrofit

import android.service.autofill.UserData
import com.example.mygithubuser.database.remote.response.ItemsItem
import com.example.mygithubuser.database.remote.response.ResponseDetail
import com.example.mygithubuser.database.remote.response.ResponseUser
import com.example.mygithubuser.ui.adapter.UserAdapter
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token API_KEY")
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<ResponseUser>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}