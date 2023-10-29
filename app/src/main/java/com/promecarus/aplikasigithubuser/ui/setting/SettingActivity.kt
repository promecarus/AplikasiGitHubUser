package com.promecarus.aplikasigithubuser.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.promecarus.aplikasigithubuser.R
import com.promecarus.aplikasigithubuser.databinding.ActivitySettingBinding
import com.promecarus.aplikasigithubuser.ui.detail.DetailActivity.Companion.FOLLOWERS_LIMIT
import com.promecarus.aplikasigithubuser.ui.detail.DetailActivity.Companion.FOLLOWING_LIMIT
import com.promecarus.aplikasigithubuser.ui.main.MainActivity.Companion.RESULT_LIMIT
import com.promecarus.aplikasigithubuser.ui.main.MainActivity.Companion.SEARCH_LIMIT
import com.promecarus.aplikasigithubuser.ui.main.MainActivity.Companion.SHARED_PREFERENCES

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

        binding.appBar.setNavigationOnClickListener { finish() }

        inputIntChecker(
            binding.textFieldResultLimit, this, sharedPreferences, RESULT_LIMIT, 30
        )

        inputIntChecker(
            binding.textFieldSearchLimit, this, sharedPreferences, SEARCH_LIMIT, 7
        )

        inputIntChecker(
            binding.textFieldFollowersLimit, this, sharedPreferences, FOLLOWERS_LIMIT, 20
        )

        inputIntChecker(
            binding.textFieldFollowingLimit, this, sharedPreferences, FOLLOWING_LIMIT, 20
        )

        binding.switchTheme.isChecked = sharedPreferences.getBoolean(DARK_MODE, false)

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(DARK_MODE, isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun inputIntChecker(
        editText: TextInputLayout,
        context: Context,
        sharedPreferences: SharedPreferences,
        key: String,
        defaultValue: Int
    ) {
        with(editText) {
            this.editText?.setText(sharedPreferences.getInt(key, defaultValue).toString())
            this.editText!!.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrBlank() -> this.error =
                        context.getString(R.string.field_cannot_be_empty)

                    text.toString().toIntOrNull() == null -> this.error =
                        context.getString(R.string.invalid_input)

                    text.toString().toInt() < 1 -> this.error =
                        context.getString(R.string.value_cannot_be_less_than_1)

                    text.toString().toInt() > 100 -> this.error =
                        context.getString(R.string.value_cannot_be_more_than_100)

                    else -> {
                        this.error = null
                        sharedPreferences.edit().putInt(key, text.toString().toInt()).apply()
                    }
                }
            }
        }
    }

    companion object {
        const val DARK_MODE = "dark_mode"
    }
}
