package com.promecarus.aplikasigithubuser.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.promecarus.aplikasigithubuser.R
import com.promecarus.aplikasigithubuser.adapter.FollowAdapter
import com.promecarus.aplikasigithubuser.databinding.ActivityDetailBinding
import com.promecarus.aplikasigithubuser.ui.setting.SettingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.setNavigationOnClickListener { finish() }

        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }

                R.id.share -> {
                    try {
                        startActivity(
                            Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(
                                Intent.EXTRA_TEXT,
                                "https://github.com/${intent.getStringExtra(EXTRA_USERNAME)}\n\nShared from ${
                                    getString(R.string.app_name)
                                } by https://github.com/promecarus (Muhammad Haikal Al Rasyid)"
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            this,
                            "Gagal membagikan profil, tidak ada aplikasi yang dapat digunakan.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    true
                }

                else -> false
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager.apply {
            adapter = FollowAdapter(
                this@DetailActivity, intent.getStringExtra(EXTRA_USERNAME)!!
            )
        }) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        ViewModelProvider(this)[DetailViewModel::class.java].apply {
            userDetail(intent.getStringExtra(EXTRA_USERNAME)!!)

            isLoading.observe(this@DetailActivity) {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }

            user.observe(this@DetailActivity) { user ->
                Glide.with(this@DetailActivity).load(user.avatarUrl).into(binding.imageView)
                binding.textViewName.text = user.name ?: user.username
                binding.textViewUsername.text = user.username
                binding.textViewFollowersFollowing.text =
                    binding.root.context.resources.getQuantityString(
                        R.plurals.follow, user.followers, user.followers, user.following
                    )
                binding.textViewRepoGist.text = buildString {
                    append(
                        binding.root.context.resources.getQuantityString(
                            R.plurals.repo, user.publicRepos, user.publicRepos
                        )
                    )
                    append(" · ")
                    append(
                        binding.root.context.resources.getQuantityString(
                            R.plurals.gist, user.publicGists, user.publicGists
                        )
                    )
                }

                CoroutineScope(Dispatchers.IO).launch {
                    var isFavorite = checkFavorite(user.id) > 0
                    withContext(Dispatchers.Main) {
                        fabFavoriteCheck(isFavorite)
                    }

                    binding.floatingActionButton.setOnClickListener {
                        if (isFavorite) removeFromFavorite(user.id) else addToFavorite(user)
                        isFavorite = !isFavorite
                        fabFavoriteCheck(isFavorite)
                    }
                }
            }
        }
    }

    private fun fabFavoriteCheck(isFavorite: Boolean) {
        binding.floatingActionButton.setImageResource(
            if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
        )
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
        const val EXTRA_USERNAME = "extra_username"
        const val FOLLOWERS_LIMIT = "followersLimit"
        const val FOLLOWING_LIMIT = "followingLimit"
    }
}
