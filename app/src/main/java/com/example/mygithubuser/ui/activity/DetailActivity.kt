package com.example.mygithubuser.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.R
import com.example.mygithubuser.database.local.entity.UserEntity
import com.example.mygithubuser.database.remote.response.ResponseDetail
import com.example.mygithubuser.databinding.ActivityDetailBinding
import com.example.mygithubuser.ui.adapter.PagerAdapter
import com.example.mygithubuser.ui.viewmodel.DetailViewModel
import com.example.mygithubuser.ui.viewmodel.FavoriteViewModel
import com.example.mygithubuser.ui.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val sectionsPagerAdapter = PagerAdapter(this@DetailActivity)

    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.elevation = 0f

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = sectionsPagerAdapter

        userLogin = intent.getStringExtra(USERNAME).toString()
        val id = intent.getIntExtra(USER_ID, 0)
        val url = intent.getStringExtra(USER_URL)

        supportActionBar?.hide()


        detailViewModel.findUser(userLogin)

        detailViewModel.listUser.observe(this) { listUser ->
            setListUser(listUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.listUser.observe(this) {
            setListUser(it)
        }

        factory = ViewModelFactory.getInstance(application)

        var favorite = false

        CoroutineScope(Dispatchers.IO).launch {
            val count = favoriteViewModel.checkFavorite(id)
            favorite = if (count > 0) {
                binding.fabAdd?.setImageResource(R.drawable.baseline_favorite_24)
                true
            } else {
                binding.fabAdd?.setImageResource(R.drawable.baseline_favorite_border_24)
                false
            }
        }

        binding.fabAdd?.setOnClickListener {
            val userEntity = UserEntity(id, userLogin, url.toString())
            favorite = !favorite
            if (favorite) {
                binding.fabAdd?.setImageResource(R.drawable.baseline_favorite_24)
                favoriteViewModel.insert(userEntity)
            } else {
                binding.fabAdd?.setImageResource(R.drawable.baseline_favorite_border_24)
                favoriteViewModel.delete(id)
            }
        }
    }

    private fun setListUser(listUser: ResponseDetail) {
        val followers = "${listUser.followers} followers"
        val following = "${listUser.following} following"
        binding.textView.text = listUser.name
        binding.textView2.text = listUser.login
        binding.textView4.text = followers
        binding.textView5.text = following

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        Glide.with(this@DetailActivity)
            .load("${listUser.avatarUrl}?v=4")
            .into(binding.imageView)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        var userLogin = String()
        const val USERNAME = "username"
        const val USER_ID = "user_id"
        const val USER_URL = "user_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_2,
            R.string.tab_text_1
        )
    }

}