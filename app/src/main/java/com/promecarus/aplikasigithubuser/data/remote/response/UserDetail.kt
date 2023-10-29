package com.promecarus.aplikasigithubuser.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    @SerializedName("login") val username: String,
    val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    val type: String,
    val name: String? = null,
    @SerializedName("public_repos") val publicRepos: Int,
    @SerializedName("public_gists") val publicGists: Int,
    val followers: Int,
    val following: Int,
) : Parcelable
