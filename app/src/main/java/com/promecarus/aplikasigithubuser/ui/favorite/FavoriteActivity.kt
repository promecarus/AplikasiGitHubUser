package com.promecarus.aplikasigithubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.promecarus.aplikasigithubuser.R
import com.promecarus.aplikasigithubuser.adapter.UserAdapter
import com.promecarus.aplikasigithubuser.databinding.ActivityFavoriteBinding
import com.promecarus.aplikasigithubuser.ui.setting.SettingActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.setNavigationOnClickListener { finish() }

        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }

                else -> false
            }
        }

        ViewModelProvider(this)[FavoriteViewModel::class.java].apply {
            listUserFavorite.observe(this@FavoriteActivity) {
                binding.recyclerView.apply {
                    adapter = UserAdapter().apply {
                        submitList(it)
                    }
                    setHasFixedSize(true)
                }
            }
        }
    }
}
