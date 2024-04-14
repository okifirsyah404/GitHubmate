package com.okifirsyah.githubmate.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.okifirsyah.githubmate.presentation.view.follower.FollowerFragment
import com.okifirsyah.githubmate.presentation.view.following.FollowingFragment

class DetailPagerAdapter(activity: FragmentActivity, private val username: String) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val args = Bundle()

        val followingFragment = FollowingFragment()
        val followerFragment = FollowerFragment()

        args.putString(EXTRA_USERNAME, username)

        followingFragment.arguments = args
        followerFragment.arguments = args

        return when (position) {
            0 -> followerFragment
            else -> followingFragment
        }
    }

    companion object {
        var EXTRA_USERNAME = "extra_username"
    }
}
