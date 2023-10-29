package com.promecarus.aplikasigithubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.promecarus.aplikasigithubuser.data.common.User

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(user: User)

    @Query("SELECT * FROM favorite_user ORDER BY id ASC")
    fun getFavoriteUser(): LiveData<List<User>>

    @Query("SELECT count(*) FROM favorite_user WHERE id = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}
