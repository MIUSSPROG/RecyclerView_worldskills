package com.example.recyclerview_worldskills.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclerview_worldskills.R
import com.example.recyclerview_worldskills.adapter.UsersAdapter
import com.example.recyclerview_worldskills.databinding.FragmentUsersListBinding

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

}