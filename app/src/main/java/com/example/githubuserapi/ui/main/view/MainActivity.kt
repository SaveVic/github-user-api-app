package com.example.githubuserapi.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.R
import com.example.githubuserapi.data.api.ApiHelper
import com.example.githubuserapi.data.api.ApiServiceImpl
import com.example.githubuserapi.data.model.QueryUser
import com.example.githubuserapi.databinding.ActivityMainBinding
import com.example.githubuserapi.ui.base.ViewModelFactory
import com.example.githubuserapi.ui.main.adapter.MainQueryAdapter
import com.example.githubuserapi.ui.main.viewmodel.MainViewModel
import com.example.githubuserapi.utils.ListLoadStatus

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainQueryAdapter
    private var emptyMsg = ""
    private var errorMsg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        emptyMsg = resources.getString(R.string.status_empty)
        errorMsg = resources.getString(R.string.status_error)
        binding.mainRv.layoutManager = LinearLayoutManager(this)
        adapter = MainQueryAdapter(arrayListOf())
        binding.mainRv.adapter = adapter
        adapter.setOnItemClickCallback(object : MainQueryAdapter.OnItemClickCallback {
            override fun onItemClicked(user: QueryUser) {
                onNavigateUser(user)
            }
        })
        binding.mainSetting.setOnClickListener { onSettingClick() }
        binding.mainSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onSearch(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupObserver() {
        mainViewModel.getListUsers().observe(this, {
            when (it.status) {
                ListLoadStatus.FILLED -> {
                    binding.mainLoading.visibility = View.GONE
                    binding.mainStatus.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.mainRv.visibility = View.VISIBLE
                }
                ListLoadStatus.EMPTY -> {
                    binding.mainLoading.visibility = View.GONE
                    binding.mainRv.visibility = View.GONE
                    binding.mainStatus.text = emptyMsg
                    binding.mainStatus.visibility = View.VISIBLE
                }
                ListLoadStatus.ERROR -> {
                    binding.mainLoading.visibility = View.GONE
                    binding.mainRv.visibility = View.GONE
                    var display = errorMsg
                    it.message?.let { msg ->
                        display += "\n$msg"
                    }
                    binding.mainStatus.text = display
                    binding.mainStatus.visibility = View.VISIBLE
                }
                ListLoadStatus.LOADING -> {
                    binding.mainLoading.visibility = View.VISIBLE
                    binding.mainRv.visibility = View.GONE
                    binding.mainStatus.visibility = View.GONE
                }
            }
        })
    }

    private fun renderList(users: List<QueryUser>) {
        adapter.replaceList(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        val token = resources.getString(R.string.github_token)
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl(token)))
        ).get(MainViewModel::class.java)
    }

    private fun onSearch(query: String?) {
        if (query == null || query.isEmpty()) return
        mainViewModel.fetchQuery(query)
    }

    private fun onSettingClick() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }

    private fun onNavigateUser(user: QueryUser) {
        val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.PARAM_USERNAME, user.login)
        startActivity(detailIntent)
    }
}