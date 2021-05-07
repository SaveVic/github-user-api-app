package com.example.githubuserapi.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.data.model.QueryUser
import com.example.githubuserapi.databinding.ItemQueryUserBinding


class MainQueryAdapter(
    private val queryUsers: ArrayList<QueryUser>,
    private val isClickable: Boolean = true
) : RecyclerView.Adapter<MainQueryAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: QueryUser)
    }

    inner class ViewHolder(private val binding: ItemQueryUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: QueryUser) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .into(itemQueryImg)
                itemQueryUsername.text = user.login
                if (isClickable) {
                    itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding =
            ItemQueryUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(queryUsers[position])
    }

    override fun getItemCount(): Int = queryUsers.size

    fun replaceList(newUsers: List<QueryUser>) {
        queryUsers.clear()
        queryUsers.addAll(newUsers)
    }
}