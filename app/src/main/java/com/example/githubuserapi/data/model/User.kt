package com.example.githubuserapi.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val login: String = "",
    @SerializedName("id") val id: Int = 0,
    @SerializedName("avatar_url") val avatar_url: String = "",
    @SerializedName("name") val name: String?,
    @SerializedName("company") val company: String = "",
    @SerializedName("blog") val blog: String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("email") val email: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("twitter_username") val twitter_username: String?,
    @SerializedName("public_repos") val public_repos: Int = 0,
    @SerializedName("public_gists") val public_gists: Int = 0,
    @SerializedName("followers") val followers: Int = 0,
    @SerializedName("following") val following: Int = 0,
)
