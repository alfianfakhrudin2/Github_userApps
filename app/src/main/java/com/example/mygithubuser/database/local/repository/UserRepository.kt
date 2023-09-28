package com.example.mygithubuser.database.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mygithubuser.database.local.entity.UserEntity
import com.example.mygithubuser.database.local.room.UserDao
import com.example.mygithubuser.database.local.room.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mFavoriteDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mFavoriteDao = db.userDao()
    }

    fun checkFavorite(id: Int) = mFavoriteDao.isUserFav(id)

    fun insert(user: UserEntity) {
        executorService.execute { mFavoriteDao.insert(user) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavoriteDao.delete(id) }
    }

    fun getFavoriteUser(): LiveData<List<UserEntity>> {
        return mFavoriteDao.getAllFavorite()
    }
}