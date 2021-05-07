package com.example.githubuserapi.data.api

class ApiHelper(private val service: ApiService) {
    fun getQueryUserResult(query: String) = service.getQueryUserResult(query)

    fun getDetailUser(username: String) = service.getDetailUser(username)

    fun getUserFollowers(username: String) = service.getUserFollowers(username)

    fun getUserFollowings(username: String) = service.getUserFollowings(username)
}