package com.promecarus.aplikasigithubuser.data.remote.retrofit

import com.promecarus.aplikasigithubuser.data.common.User
import com.promecarus.aplikasigithubuser.data.remote.response.SearchUsers
import com.promecarus.aplikasigithubuser.data.remote.response.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String, @Query("per_page") perPage: Int
    ): Call<SearchUsers>

    @GET("users/{username}")
    fun userDetail(
        @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    fun userFollowers(
        @Path("username") username: String, @Query("per_page") perPage: Int
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun userFollowing(
        @Path("username") username: String, @Query("per_page") perPage: Int
    ): Call<List<User>>
}
