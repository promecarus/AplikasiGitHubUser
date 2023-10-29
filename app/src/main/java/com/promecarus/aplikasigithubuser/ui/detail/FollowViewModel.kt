package com.promecarus.aplikasigithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promecarus.aplikasigithubuser.data.common.User
import com.promecarus.aplikasigithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<List<User>>()
    val followers: MutableLiveData<List<User>> = _followers

    private val _following = MutableLiveData<List<User>>()
    val following: MutableLiveData<List<User>> = _following

    fun userFollowers(
        username: String, perPage: Int
    ) {
        _isLoading.value = true
        ApiConfig.getApiService().userFollowers(username, perPage)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followers.postValue(response.body() as List<User>)
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, t.message.toString())
                }
            })
    }

    fun userFollowing(
        username: String, perPage: Int
    ) {
        _isLoading.value = true
        ApiConfig.getApiService().userFollowing(username, perPage)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _following.postValue(response.body() as List<User>)
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, t.message.toString())
                }
            })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}
