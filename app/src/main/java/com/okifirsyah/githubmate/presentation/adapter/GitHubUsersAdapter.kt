package com.okifirsyah.githubmate.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.network.response.GitHubUserResponse
import com.okifirsyah.githubmate.databinding.ItemUserBinding

class GitHubUsersAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<GitHubUsersAdapter.ViewHolder>() {

    private var _items: ArrayList<GitHubUserResponse> = ArrayList()


    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: ArrayList<GitHubUserResponse>) {
        _items.clear()
        _items.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        _items.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ItemUserBinding.bind(holder.itemView)

        val item = _items[position]

        binding.apply {
            tvUserName.text = item.username
            civUser.load(item.avatarURL) {
                placeholder(R.drawable.github_logo)
            }

            root.setOnClickListener {
                onClick(item.username)
            }
        }

    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

}


