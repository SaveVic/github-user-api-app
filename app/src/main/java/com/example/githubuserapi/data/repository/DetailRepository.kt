package com.example.githubuserapi.data.repository

import com.example.githubuserapi.data.api.ApiHelper
import com.example.githubuserapi.data.model.QueryUser
import com.example.githubuserapi.data.model.User
import io.reactivex.Single

class DetailRepository(private val apiHelper: ApiHelper) {
    fun getDetailUser(username: String): Single<User> {
        return apiHelper.getDetailUser(username)
    }

    fun getUserFollowers(username: String): Single<List<QueryUser>> {
        return apiHelper.getUserFollowers(username)
    }

    fun getUserFollowings(username: String): Single<List<QueryUser>> {
        return apiHelper.getUserFollowings(username)
    }
}