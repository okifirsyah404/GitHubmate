package com.okifirsyah.githubmate.presentation.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.okifirsyah.githubmate.R
import com.okifirsyah.githubmate.data.model.GitHubUser
import com.okifirsyah.githubmate.databinding.ItemUserBinding
import com.okifirsyah.githubmate.resource.constant.DurationConstant
import com.okifirsyah.githubmate.utils.extension.animateSplash
import com.okifirsyah.githubmate.utils.extension.gone

class UserFollowerAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<UserFollowerAdapter.ViewHolder>() {

    private var _items: ArrayList<GitHubUser> = ArrayList()

    fun setItems(data: ArrayList<GitHubUser>) {
        _items.clear()
        _items.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = _items[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GitHubUser) {
            binding.apply {
                tvUserName.text = item.username
                civUser.load(item.avatarURL) {
                    placeholder(R.drawable.github_logo)
                }
                btnFavorite.gone()
                root.setOnClickListener {
                    it.animateSplash()
                    Handler(Looper.getMainLooper()).postDelayed({
                        onClick(item.username)
                    }, DurationConstant.SPLASH_ANIMATE_DURATION)
                }
            }
        }
    }

}
