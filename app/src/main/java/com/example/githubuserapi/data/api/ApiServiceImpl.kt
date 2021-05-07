package com.example.githubuserapi.data.api

import android.content.res.Resources
import com.example.githubuserapi.R
import com.example.githubuserapi.data.model.QueryResult
import com.example.githubuserapi.data.model.QueryUser
import com.example.githubuserapi.data.model.User
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl(private val token: String) : ApiService{

    private val baseUrl = "https://api.github.com"

    override fun getQueryUserResult(query: String): Single<QueryResult> {
        return Rx2AndroidNetworking.get("$baseUrl/search/users?q=$query")
            .addHeaders("Authorization", "token $token")
            .build()
            .getObjectSingle(QueryResult::class.java)
    }

    override fun getDetailUser(username: String): Single<User> {
        return Rx2AndroidNetworking.get("$baseUrl/users/$username")
            .addHeaders("Authorization", "token $token")
            .build()
            .getObjectSingle(User::class.java)
    }

    override fun getUserFollowers(username: String): Single<List<QueryUser>> {
        return Rx2AndroidNetworking.get("$baseUrl/users/$username/followers")
            .addHeaders("Authorization", "token $token")
            .build()
            .getObjectListSingle(QueryUser::class.java)
    }

    override fun getUserFollowings(username: String): Single<List<QueryUser>> {
        return Rx2AndroidNetworking.get("$baseUrl/users/$username/following")
            .addHeaders("Authorization", "token $token")
            .build()
            .getObjectListSingle(QueryUser::class.java)
    }

}