package com.example.recyclerview_worldskills.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.recyclerview_worldskills.App
import com.example.recyclerview_worldskills.R
import com.example.recyclerview_worldskills.adapter.UserActionListener
import com.example.recyclerview_worldskills.adapter.UsersAdapter
import com.example.recyclerview_worldskills.databinding.FragmentUsersListBinding
import com.example.recyclerview_worldskills.model.User
import com.example.recyclerview_worldskills.model.UsersListener
import com.example.recyclerview_worldskills.model.UsersService
import com.example.recyclerview_worldskills.utils.factory
import com.example.recyclerview_worldskills.utils.navigator

class UserListFragment : Fragment(R.layout.fragment_users_list) {

    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersAdapter

    private val viewModel: UsersListViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)

        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                viewModel.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                navigator().showDetails(user)
            }

            override fun onUserFire(user: User) {
                viewModel.fireUser(user)
            }

        })


        val itemAnimator = binding.recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator){
            itemAnimator.supportsChangeAnimations = false
        }


        viewModel.users.observe(viewLifecycleOwner, Observer { result ->
            adapter.users = result
        })

        binding.recyclerView.adapter = adapter


        return binding.root
    }


}