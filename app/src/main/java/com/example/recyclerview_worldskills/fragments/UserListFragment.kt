package com.example.recyclerview_worldskills.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.recyclerview_worldskills.App
import com.example.recyclerview_worldskills.R
import com.example.recyclerview_worldskills.adapter.UserActionListener
import com.example.recyclerview_worldskills.adapter.UsersAdapter
import com.example.recyclerview_worldskills.databinding.FragmentUsersListBinding
import com.example.recyclerview_worldskills.model.User
import com.example.recyclerview_worldskills.model.UsersListener
import com.example.recyclerview_worldskills.model.UsersService

class UserListFragment : Fragment(R.layout.fragment_users_list) {

    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (requireActivity().applicationContext as App).usersService

    private val usersListener: UsersListener = {
        adapter.users = it
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)

        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                usersService.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                Toast.makeText(requireContext(), "User: ${user.name}", Toast.LENGTH_SHORT).show()
            }

        })

        binding.recyclerView.adapter = adapter
        usersService.addListener(usersListener)


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }
}