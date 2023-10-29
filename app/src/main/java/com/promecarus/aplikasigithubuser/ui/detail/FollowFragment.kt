package com.promecarus.aplikasigithubuser.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.promecarus.aplikasigithubuser.adapter.UserAdapter
import com.promecarus.aplikasigithubuser.data.common.User
import com.promecarus.aplikasigithubuser.databinding.FragmentFollowBinding
import com.promecarus.aplikasigithubuser.ui.detail.DetailActivity.Companion.FOLLOWERS_LIMIT
import com.promecarus.aplikasigithubuser.ui.detail.DetailActivity.Companion.FOLLOWING_LIMIT
import com.promecarus.aplikasigithubuser.ui.main.MainActivity.Companion.SHARED_PREFERENCES

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        ViewModelProvider(
            requireActivity(), ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java].apply {
            if (arguments?.getInt(ARG_POSITION) == 1) {
                userFollowers(
                    arguments?.getString(ARG_USERNAME)!!,
                    sharedPreferences.getInt(FOLLOWERS_LIMIT, 20)
                )
                populate(followers)
            } else {
                userFollowing(
                    arguments?.getString(ARG_USERNAME)!!,
                    sharedPreferences.getInt(FOLLOWING_LIMIT, 20)
                )
                populate(following)
            }

            isLoading.observe(requireActivity()) {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun populate(data: MutableLiveData<List<User>>) {
        data.observe(requireActivity()) {
            binding.recyclerView.apply {
                adapter = UserAdapter().apply { submitList(it) }
                setHasFixedSize(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "extra_position"
        const val ARG_USERNAME = "extra_username"
    }
}
