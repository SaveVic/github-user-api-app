package com.example.githubuserapi.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.data.model.QueryUser
import com.example.githubuserapi.data.model.User
import com.example.githubuserapi.data.repository.DetailRepository
import com.example.githubuserapi.utils.ResourceQuery
import com.example.githubuserapi.utils.ResourceUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val detailRepository: DetailRepository, private val username: String) : ViewModel() {
    private val infoUser = MutableLiveData<ResourceUser<User>>()
    private val infoFollowersList = MutableLiveData<ResourceQuery<List<QueryUser>>>()
    private val infoFollowingsList = MutableLiveData<ResourceQuery<List<QueryUser>>>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    init {
        fetchData()
        fetchFollowings()
        fetchFollowers()
    }

    private fun fetchData() {
        infoUser.postValue(ResourceUser.loading(null))
        compositeDisposable.add(
            detailRepository.getDetailUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    infoUser.postValue(ResourceUser.success(result))
                }, { throwable ->
                    infoUser.postValue(
                        ResourceUser.error(
                            null,
                            throwable.message ?: ""
                        )
                    )
                })
        )
    }

    private fun fetchFollowings() {
        infoFollowingsList.postValue(ResourceQuery.loading(null))
        compositeDisposable.add(
            detailRepository.getUserFollowings(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    when (result.size) {
                        0 -> infoFollowingsList.postValue(ResourceQuery.empty(null))
                        else -> infoFollowingsList.postValue(ResourceQuery.filled(result))
                    }
                }, { throwable ->
                    infoFollowingsList.postValue(
                        ResourceQuery.error(
                            null,
                            throwable.message ?: ""
                        )
                    )
                })
        )
    }

    private fun fetchFollowers() {
        infoFollowersList.postValue(ResourceQuery.loading(null))
        compositeDisposable.add(
            detailRepository.getUserFollowers(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    when (result.size) {
                        0 -> infoFollowersList.postValue(ResourceQuery.empty(null))
                        else -> infoFollowersList.postValue(ResourceQuery.filled(result))
                    }
                }, { throwable ->
                    infoFollowersList.postValue(
                        ResourceQuery.error(
                            null,
                            throwable.message ?: ""
                        )
                    )
                })
        )
    }

    fun getInfoUser(): LiveData<ResourceUser<User>> {
        return infoUser
    }

    fun getFollowersList(): LiveData<ResourceQuery<List<QueryUser>>> {
        return infoFollowersList
    }

    fun getFollowingsList(): LiveData<ResourceQuery<List<QueryUser>>> {
        return infoFollowingsList
    }
}