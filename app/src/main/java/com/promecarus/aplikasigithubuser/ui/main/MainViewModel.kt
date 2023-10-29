package com.promecarus.aplikasigithubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.promecarus.aplikasigithubuser.data.common.User
import com.promecarus.aplikasigithubuser.data.remote.response.SearchUsers
import com.promecarus.aplikasigithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUserResult = MutableLiveData<List<User>>()
    val listUserResult: LiveData<List<User>> = _listUserResult

    private val _listUserSearch = MutableLiveData<List<User>>()
    val listUserSearch: LiveData<List<User>> = _listUserSearch

    fun searchUsers(
        query: String, to: String, perPage: Int
    ) {
        _isLoading.value = true
        ApiConfig.getApiService().searchUsers(query, perPage)
            .enqueue(object : Callback<SearchUsers> {
                override fun onResponse(call: Call<SearchUsers>, response: Response<SearchUsers>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        (when (to) {
                            RESULT -> _listUserResult
                            SEARCH -> _listUserSearch
                            else -> return
                        }).postValue(response.body()?.users)
                    }
                }

                override fun onFailure(call: Call<SearchUsers>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, t.message.toString())
                }
            })
    }

    companion object {
        private const val TAG = "MainViewModel"
        const val RESULT = "result"
        const val SEARCH = "search"
    }
}
