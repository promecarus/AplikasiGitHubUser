package com.promecarus.aplikasigithubuser.data.remote.response

import com.google.gson.annotations.SerializedName
import com.promecarus.aplikasigithubuser.data.common.User

data class SearchUsers(
    @SerializedName("items") val users: List<User>? = null,
)
