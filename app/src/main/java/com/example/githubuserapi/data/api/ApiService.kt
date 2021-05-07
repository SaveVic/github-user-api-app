package com.example.githubuserapi.data.api

import com.example.githubuserapi.data.model.QueryResult
import com.example.githubuserapi.data.model.QueryUser
import com.example.githubuserapi.data.model.User
import io.reactivex.Single

interface ApiService {

    fun getQueryUserResult(query: String): Single<QueryResult>

    fun getDetailUser(username: String): Single<User>

    fun getUserFollowers(username: String): Single<List<QueryUser>>

    fun getUserFollowings(username: String): Single<List<QueryUser>>
}