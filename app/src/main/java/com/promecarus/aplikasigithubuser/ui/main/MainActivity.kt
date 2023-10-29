package com.promecarus.aplikasigithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.promecarus.aplikasigithubuser.R
import com.promecarus.aplikasigithubuser.adapter.UserAdapter
import com.promecarus.aplikasigithubuser.databinding.ActivityMainBinding
import com.promecarus.aplikasigithubuser.ui.detail.DetailActivity.Companion.EXTRA_USERNAME
import com.promecarus.aplikasigithubuser.ui.favorite.FavoriteActivity
import com.promecarus.aplikasigithubuser.ui.setting.SettingActivity
import com.promecarus.aplikasigithubuser.ui.setting.SettingActivity.Companion.DARK_MODE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

        AppCompatDelegate.setDefaultNightMode(
            if (sharedPreferences.getBoolean(DARK_MODE, false)) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java].apply {
            searchUsers(
                sharedPreferences.getString(EXTRA_USERNAME, "prome")!!,
                MainViewModel.RESULT,
                sharedPreferences.getInt(RESULT_LIMIT, 100)
            )

            isLoading.observe(this@MainActivity) {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }

            listUserResult.observe(this@MainActivity) {
                binding.recyclerViewMain.apply {
                    adapter = UserAdapter().apply {
                        submitList(it)
                    }
                    setHasFixedSize(true)
                }
            }

            listUserSearch.observe(this@MainActivity) {
                binding.recyclerViewSearch.apply {
                    adapter = UserAdapter().apply {
                        submitList(it)
                    }
                    setHasFixedSize(true)
                }
            }

            binding.searchView.editText.setOnEditorActionListener { textView, _, _ ->
                binding.searchBar.text = textView.text
                binding.searchView.hide()
                if (textView.text.isNotBlank()) {
                    searchUsers(
                        textView.text.toString(),
                        MainViewModel.RESULT,
                        sharedPreferences.getInt(RESULT_LIMIT, 100)
                    )
                    sharedPreferences.edit().putString(EXTRA_USERNAME, textView.text.toString())
                        .apply()
                }
                false
            }

            binding.searchView.editText.addTextChangedListener {
                searchJob?.cancel()
                if (it.toString().isNotBlank()) searchJob =
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(250)
                        if (it.toString() == binding.searchView.editText.text.toString()) searchUsers(
                            it.toString().trim(),
                            MainViewModel.SEARCH,
                            sharedPreferences.getInt(SEARCH_LIMIT, 100)
                        )
                    }
            }
        }

        binding.searchBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    true
                }

                R.id.setting -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    companion object {
        const val SHARED_PREFERENCES = "promecarus"
        const val RESULT_LIMIT = "resultLimit"
        const val SEARCH_LIMIT = "searchLimit"
    }
}
