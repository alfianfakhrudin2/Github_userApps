package com.example.mygithubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.database.remote.response.ItemsItem
import com.example.mygithubuser.databinding.FragmentDetailBinding
import com.example.mygithubuser.ui.adapter.UserAdapter
import com.example.mygithubuser.ui.activity.DetailActivity
import com.example.mygithubuser.ui.viewmodel.FollowerslViewModel
import com.example.mygithubuser.ui.viewmodel.FollowingViewModel


class DetailFragment : Fragment() {
    private val followingViewModel by viewModels<FollowingViewModel>()

    private val followersViewModel by viewModels<FollowerslViewModel>()

    private lateinit var binding: FragmentDetailBinding

    private val adapterUser = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_POSITION) ?: 0

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvDetail.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvDetail.addItemDecoration(itemDecoration)

        if (position == 1) {
            followingViewModel.getFollowing(DetailActivity.userLogin)
            followingViewModel.listUser.observe(viewLifecycleOwner) { following ->
                showFollowingViewModel(following)
            }
            followingViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            followersViewModel.getFollowers(DetailActivity.userLogin)
            followersViewModel.listUser.observe(viewLifecycleOwner) { followers ->
                showFollowersViewModel(followers)
            }
            followersViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun showFollowingViewModel(item: List<ItemsItem>) {
        if (item.isNotEmpty()) {
            binding.rvDetail.visibility = View.VISIBLE
            adapterUser.submitList(item)
            binding.rvDetail.adapter = adapterUser
        }
    }

    private fun showFollowersViewModel(item: List<ItemsItem>) {
        if (item.isNotEmpty()) {
            binding.rvDetail.visibility = View.VISIBLE
            adapterUser.submitList(item)
            binding.rvDetail.adapter = adapterUser
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar5.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

}