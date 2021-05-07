package com.example.githubuserapi.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.data.model.QueryUser
import com.example.githubuserapi.data.repository.MainRepository
import com.example.githubuserapi.utils.ResourceQuery
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val queryListUsers = MutableLiveData<ResourceQuery<List<QueryUser>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        initialQuery()
    }

    private fun initialQuery() {
        queryListUsers.postValue(ResourceQuery.empty(null))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun fetchQuery(query: String) {
        queryListUsers.postValue(ResourceQuery.loading(null))
        compositeDisposable.add(
            mainRepository.getQueryUserResult(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    when (result.total_count) {
                        0 -> queryListUsers.postValue(ResourceQuery.empty(null))
                        else -> queryListUsers.postValue(ResourceQuery.filled(result.items))
                    }

                }, { throwable ->
                    queryListUsers.postValue(
                        ResourceQuery.error(
                            null,
                            throwable.message ?: ""
                        )
                    )
                })
        )
    }

    fun getListUsers(): LiveData<ResourceQuery<List<QueryUser>>> {
        return queryListUsers
    }

}