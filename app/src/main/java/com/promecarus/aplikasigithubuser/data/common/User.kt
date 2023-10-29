package com.promecarus.aplikasigithubuser.data.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_user")
data class User(
    @SerializedName("login") val username: String,
    @PrimaryKey val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    val type: String
)
