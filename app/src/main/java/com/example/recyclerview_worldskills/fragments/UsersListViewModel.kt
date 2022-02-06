package com.example.recyclerview_worldskills.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview_worldskills.model.User
import com.example.recyclerview_worldskills.model.UsersListener
import com.example.recyclerview_worldskills.model.UsersService

class UsersListViewModel(
    private val usersService: UsersService
): ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val listener: UsersListener = {
        _users.value = it
    }

    init {
        loadUsers()
    }

    private fun loadUsers() {
        usersService.addListener(listener)
    }

    override fun onCleared() {
        super.onCleared()
        usersService.removeListener(listener)
    }

    fun moveUser(user: User, moveBy: Int){
        usersService.moveUser(user, moveBy)
    }

    fun deleteUser(user: User){
        usersService.deleteUser(user)
    }

    fun fireUser(user: User){
        usersService.fireUser(user)
    }
}