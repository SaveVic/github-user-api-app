package com.example.githubuserapi.data.repository

import com.example.githubuserapi.data.api.ApiHelper
import com.example.githubuserapi.data.model.QueryResult
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getQueryUserResult(query: String): Single<QueryResult>{
        return apiHelper.getQueryUserResult(query)
    }
}