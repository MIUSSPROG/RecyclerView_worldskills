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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_worldskills.App
import com.example.recyclerview_worldskills.R
import com.example.recyclerview_worldskills.adapter.UserActionListener
import com.example.recyclerview_worldskills.adapter.UserListAdapter
import com.example.recyclerview_worldskills.databinding.FragmentUsersListBinding
import com.example.recyclerview_worldskills.model.User
import com.example.recyclerview_worldskills.model.UsersListener
import com.example.recyclerview_worldskills.model.UsersService
import com.example.recyclerview_worldskills.utils.factory
import com.example.recyclerview_worldskills.utils.navigator

class UserListFragment : Fragment(R.layout.fragment_users_list) {

    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UserListAdapter

    private val viewModel: UsersListViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)

        adapter = UserListAdapter(object : UserActionListener {
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
            adapter.submitList(result)
        })

        binding.recyclerView.adapter = adapter


        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags =  ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.START
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                source: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPos = source.adapterPosition
                val endPos = target.adapterPosition
                viewModel.swapUsers(startPos, endPos)
                recyclerView.adapter?.notifyItemMoved(startPos, endPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val userToDelete = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteUser(userToDelete)
                Toast.makeText(requireActivity(), "User ${userToDelete.name} has been deleted", Toast.LENGTH_SHORT).show()
            }

        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        return binding.root
    }


}