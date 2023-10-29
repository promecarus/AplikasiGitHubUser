package com.promecarus.aplikasigithubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.promecarus.aplikasigithubuser.data.common.User
import com.promecarus.aplikasigithubuser.data.local.room.FavoriteUserDatabase
import com.promecarus.aplikasigithubuser.data.remote.response.UserDetail
import com.promecarus.aplikasigithubuser.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<UserDetail>()
    val user: LiveData<UserDetail> = _user

    private val favoriteUserDao = FavoriteUserDatabase.getDatabase(application).favoriteUserDao()

    fun userDetail(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().userDetail(username).enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }
        })
    }

    fun addToFavorite(userDetail: UserDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao.addToFavorite(with(userDetail) {
                User(username, id, avatarUrl, type)
            })
        }
    }

    suspend fun checkFavorite(id: Int) = favoriteUserDao.checkFavorite(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao.removeFromFavorite(id)
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}
