package com.example.recyclerview_worldskills.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview_worldskills.model.UserDetails
import com.example.recyclerview_worldskills.model.UsersService

class UserDetailsViewModel(
    private val userService: UsersService
): ViewModel() {

    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    fun loadUser(userId: Long){
        _userDetails.value = userService.getById(userId)
    }

    fun deleteUser(){
        val userDetails = userDetails.value ?: return
        userService.deleteUser(userDetails.user)
    }
}