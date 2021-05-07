package com.example.githubuserapi.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapi.R
import com.example.githubuserapi.data.model.QueryUser
import com.example.githubuserapi.databinding.FragmentFollowersBinding
import com.example.githubuserapi.ui.main.adapter.MainQueryAdapter
import com.example.githubuserapi.ui.main.viewmodel.DetailViewModel
import com.example.githubuserapi.utils.ListLoadStatus

class FollowersFragment : Fragment() {
    private var binding: FragmentFollowersBinding? = null
    private var adapter: MainQueryAdapter? = null
    private var detailViewModel: DetailViewModel? = null
    private var emptyMsg = ""
    private var errorMsg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = activity?.let { ViewModelProviders.of(it).get(DetailViewModel::class.java) }
        emptyMsg = resources.getString(R.string.status_empty)
        errorMsg = resources.getString(R.string.status_error)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        setupUI()
        setupObserver()
        return binding?.root
    }
    
    private fun setupUI(){
        binding?.followersRv?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapter = MainQueryAdapter(arrayListOf(), false)
        binding?.followersRv?.adapter = adapter
    }

    private fun renderList(users: List<QueryUser>) {
        adapter?.replaceList(users)
        adapter?.notifyDataSetChanged()
    }

    private fun setupObserver() {
        detailViewModel?.getFollowersList()?.observe(this, {
            when (it.status) {
                ListLoadStatus.FILLED -> {
                    binding?.followersLoading?.visibility = View.GONE
                    binding?.followersStatus?.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding?.followersRv?.visibility = View.VISIBLE
                }
                ListLoadStatus.EMPTY -> {
                    binding?.followersLoading?.visibility = View.GONE
                    binding?.followersRv?.visibility = View.GONE
                    binding?.followersStatus?.text = emptyMsg
                    binding?.followersStatus?.visibility = View.VISIBLE
                }
                ListLoadStatus.ERROR -> {
                    binding?.followersLoading?.visibility = View.GONE
                    binding?.followersRv?.visibility = View.GONE
                    var display = errorMsg
                    it.message?.let { msg ->
                        display += "\n$msg"
                    }
                    binding?.followersStatus?.text = display
                    binding?.followersStatus?.visibility = View.VISIBLE
                }
                ListLoadStatus.LOADING -> {
                    binding?.followersLoading?.visibility = View.VISIBLE
                    binding?.followersRv?.visibility = View.GONE
                    binding?.followersStatus?.visibility = View.GONE
                }
            }
        })
    }
}