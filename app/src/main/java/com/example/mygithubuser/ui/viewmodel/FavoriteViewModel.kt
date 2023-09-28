package com.example.mygithubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.database.local.entity.UserEntity
import com.example.mygithubuser.database.local.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mUserRepository: UserRepository = UserRepository(application)

    fun getFavo(): LiveData<List<UserEntity>> {
        _isLoading.value = false
        return mUserRepository.getFavoriteUser()
    }

    fun checkFavorite(id: Int) = mUserRepository.checkFavorite(id)

    fun insert(user: UserEntity) {
        mUserRepository.insert(user)
    }

    fun delete(id: Int) {
        mUserRepository.delete(id)
    }
}