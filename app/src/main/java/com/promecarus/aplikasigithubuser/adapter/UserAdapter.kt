package com.promecarus.aplikasigithubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.promecarus.aplikasigithubuser.R
import com.promecarus.aplikasigithubuser.data.common.User
import com.promecarus.aplikasigithubuser.databinding.ItemUserBinding
import com.promecarus.aplikasigithubuser.ui.detail.DetailActivity

class UserAdapter :
    ListAdapter<User, UserAdapter.UserViewHolder>(object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(old: User, new: User) = old == new
        override fun areContentsTheSame(old: User, new: User) = old == new
    }) {
    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide.with(binding.root.context).load(user.avatarUrl).centerCrop()
                .into(binding.imageView)
            binding.textViewUsername.text = user.username
            binding.textViewId.text =
                binding.root.context.getString(R.string.info, user.id, user.type)
            binding.root.setOnClickListener {
                binding.root.context.startActivity(Intent(
                    binding.root.context, DetailActivity::class.java
                ).apply { putExtra(DetailActivity.EXTRA_USERNAME, user.username) })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
