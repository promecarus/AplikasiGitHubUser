package com.promecarus.aplikasigithubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.promecarus.aplikasigithubuser.ui.detail.FollowFragment

class FollowAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int) = FollowFragment().apply {
        arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
            putString(FollowFragment.ARG_USERNAME, username)
        }
    }
}
