package com.example.githubuserapi.ui.main.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapi.ui.main.view.FollowersFragment
import com.example.githubuserapi.ui.main.view.FollowingsFragment

class DetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingsFragment()
        }
        return fragment as Fragment
    }
}