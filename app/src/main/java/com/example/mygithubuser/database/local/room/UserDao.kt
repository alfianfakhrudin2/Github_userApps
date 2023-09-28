package com.example.mygithubuser.database.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mygithubuser.database.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: UserEntity)

    @Query("DELETE FROM UserEntity WHERE UserEntity.id = :id")
    fun delete(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM userentity WHERE userentity.id = :id)")
    fun isUserFav(id: Int): Int

    @Query("SELECT * FROM userentity")
    fun getAllFavorite(): LiveData<List<UserEntity>>
}