package com.promecarus.aplikasigithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.promecarus.aplikasigithubuser.data.local.room.FavoriteUserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteUserDao = FavoriteUserDatabase.getDatabase(application).favoriteUserDao()

    val listUserFavorite = favoriteUserDao.getFavoriteUser()
}
