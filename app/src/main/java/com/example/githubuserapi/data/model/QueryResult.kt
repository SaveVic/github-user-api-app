package com.example.githubuserapi.data.model

import com.google.gson.annotations.SerializedName

data class QueryResult(
    @SerializedName("total_count") val total_count: Int = 0,
    @SerializedName("incomplete_results") val incomplete_results: Boolean = false,
    @SerializedName("items") val items: List<QueryUser> = listOf(),
)

data class QueryUser(
    @SerializedName("login") val login: String = "",
    @SerializedName("id") val id: Int = 0,
    @SerializedName("node_id") val node_id: String = "",
    @SerializedName("avatar_url") val avatar_url: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("html_url") val html_url: String = "",
)


