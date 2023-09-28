package com.example.mygithubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.database.remote.response.ItemsItem
import com.example.mygithubuser.databinding.ActivityMainBinding
import com.example.mygithubuser.databinding.ActivityThemeBinding
import com.example.mygithubuser.ui.viewmodel.MainViewModel
import com.example.mygithubuser.ui.adapter.UserAdapter
import com.example.mygithubuser.ui.other.SettingPreferences
import com.example.mygithubuser.ui.other.dataStore
import com.example.mygithubuser.ui.viewmodel.ThemeViewModel
import com.example.mygithubuser.ui.viewmodel.ThemeModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var bindingTheme: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindingTheme = ActivityThemeBinding.inflate(layoutInflater)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvMain.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val searchBar = searchView.text
                    searchView.hide()
                    mainViewModel.findUser(searchBar.toString())
                    false
                }

            searchBar.inflateMenu(R.menu.menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_favorite -> {
                        val moveIntentActivity =
                            Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(moveIntentActivity)
                    }

                    R.id.action_list -> {
                        val moveIntentActivity =
                            Intent(this@MainActivity, ThemeActivity::class.java)
                        startActivity(moveIntentActivity)
                    }
                }
                true
            }
        }
        mainViewModel.listUser.observe(this) { listUser ->
            setListUser(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val tema = bindingTheme.switchTheme
        theme(tema)

    }

    private fun theme(switchMaterial: SwitchMaterial) {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel =
            ViewModelProvider(this, ThemeModelFactory(pref))[ThemeViewModel::class.java]
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchMaterial.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchMaterial.isChecked = false
            }
        }
        switchMaterial.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun setListUser(listUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding.rvMain.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}