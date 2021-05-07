package com.example.githubuserapi.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.data.api.ApiHelper
import com.example.githubuserapi.data.api.ApiServiceImpl
import com.example.githubuserapi.data.model.User
import com.example.githubuserapi.databinding.ActivityDetailBinding
import com.example.githubuserapi.ui.base.DetailViewModelFactory
import com.example.githubuserapi.ui.main.adapter.DetailPagerAdapter
import com.example.githubuserapi.ui.main.viewmodel.DetailViewModel
import com.example.githubuserapi.utils.NetworkStatus
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var username: String
    private var errorMsg = ""
    private var noName = ""
    private var noEmail = ""

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.detail_tab_1,
            R.string.detail_tab_2,
        )
        const val PARAM_USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(PARAM_USERNAME) ?: ""

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        noName = resources.getString(R.string.detail_no_name)
        noEmail = resources.getString(R.string.detail_no_email)
        errorMsg = resources.getString(R.string.status_error)
        val pagerAdapter = DetailPagerAdapter(this)
        binding.detailPager.adapter = pagerAdapter
        TabLayoutMediator(binding.detailTabs, binding.detailPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        binding.detailBack.setOnClickListener { finish() }
        binding.detailSetting.setOnClickListener { onSettingClick() }
    }

    private fun onSettingClick() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }

    private fun setupViewModel() {
        val token = resources.getString(R.string.github_token)
        detailViewModel = ViewModelProviders.of(
            this,
            DetailViewModelFactory(ApiHelper(ApiServiceImpl(token)), username)
        ).get(DetailViewModel::class.java)
    }

    private fun setupObserver() {
        detailViewModel.getInfoUser().observe(this, {
            when (it.status) {
                NetworkStatus.SUCCESS -> {
                    binding.detailLoading.visibility = View.GONE
                    binding.detailStatus.visibility = View.GONE
                    it.data?.let { info -> renderInfoUser(info) }
                    binding.detailInfo.visibility = View.VISIBLE
                }
                NetworkStatus.LOADING -> {
                    binding.detailLoading.visibility = View.VISIBLE
                    binding.detailStatus.visibility = View.GONE
                    binding.detailInfo.visibility = View.INVISIBLE
                }
                NetworkStatus.ERROR -> {
                    binding.detailLoading.visibility = View.GONE
                    binding.detailInfo.visibility = View.INVISIBLE
                    var display = errorMsg
                    it.message?.let { msg ->
                        display += "\n$msg"
                    }
                    binding.detailStatus.text = display
                    binding.detailStatus.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun renderInfoUser(info: User) {
        Glide.with(this)
            .load(info.avatar_url)
            .into(binding.detailImg)
        val name = info.name ?: ""
        binding.detailName.text = if (name.isNotEmpty()) name else "-$noName-"
        val email = info.email ?: ""
        binding.detailEmail.text = if (email.isNotEmpty()) email else "-$noEmail-"
        binding.detailFollowers.text = info.followers.toString()
        binding.detailFollowings.text = info.following.toString()
    }
}