package com.example.githubuserapi.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapi.data.api.ApiHelper
import com.example.githubuserapi.data.repository.DetailRepository
import com.example.githubuserapi.ui.main.viewmodel.DetailViewModel

class DetailViewModelFactory(private val apiHelper: ApiHelper, private val username: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(DetailRepository(apiHelper), username) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}