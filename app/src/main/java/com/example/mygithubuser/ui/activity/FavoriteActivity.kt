package com.example.mygithubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.database.local.entity.UserEntity
import com.example.mygithubuser.databinding.ActivityFavoriteBinding
import com.example.mygithubuser.ui.adapter.FavoriteAdapter
import com.example.mygithubuser.ui.viewmodel.FavoriteViewModel
import com.example.mygithubuser.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factor: ViewModelFactory = ViewModelFactory.getInstance(application)
        val favoriteViewModel: FavoriteViewModel by viewModels { factor }

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        favoriteViewModel.getFavo().observe(this) { listUser ->
            setListUser(listUser)
        }

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val actionbar = supportActionBar
        actionbar!!.title = "Favorite page"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    private fun setListUser(listUser: List<UserEntity>) {
        val adapter = FavoriteAdapter()
        adapter.setListNotes(listUser)
        binding.rvFavorite.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar5.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}