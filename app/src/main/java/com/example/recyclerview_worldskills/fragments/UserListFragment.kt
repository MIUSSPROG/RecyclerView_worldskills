package com.example.recyclerview_worldskills.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclerview_worldskills.App
import com.example.recyclerview_worldskills.R
import com.example.recyclerview_worldskills.adapter.UsersAdapter
import com.example.recyclerview_worldskills.databinding.FragmentUsersListBinding
import com.example.recyclerview_worldskills.model.UsersService

class UserListFragment : Fragment(R.layout.fragment_users_list) {

    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (requireActivity().applicationContext as App).usersService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)

        adapter = UsersAdapter()
        adapter.users = usersService.getUsers()
        binding.recyclerView.adapter = adapter


        return binding.root
    }
}